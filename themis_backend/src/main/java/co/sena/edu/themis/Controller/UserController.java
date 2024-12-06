package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.UserBusiness;
import co.sena.edu.themis.Dto.PersonDto;
import co.sena.edu.themis.Dto.ProgramDto;
import co.sena.edu.themis.Dto.RoleDto;
import co.sena.edu.themis.Dto.UserDto;
import co.sena.edu.themis.Util.Exception.CustomException;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserBusiness userBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllUser() {
        try {
            List<UserDto> users = userBusiness.findAll(0, 10).getContent();
            List<Map<String, Object>> data = users.stream()
                    .map(this::convertUserDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Users retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> getUserById(@PathVariable Long id) {
        try {
            UserDto userDtos = userBusiness.findById(id);
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("User retrieved successfully", convertUserDtoToMap(userDtos), HttpStatus.OK));
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @GetMapping("/findPersonById/{id}")
    public ResponseEntity<Map<String, Object>> getPersonById(@PathVariable Long id) {
        try {
            UserDto userDto = userBusiness.findByPersonId(id);
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("User - Person retrived successfully", convertUserDtoToMap(userDto), HttpStatus.OK));
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody Map<String, Object> json) {
        try {
            UserDto userDto = convertMapToUserDto(json);
            boolean userCreated = userBusiness.createUser(userDto);
            if (userCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("User created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("User creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateUser(@PathVariable Long id, @RequestBody Map<String, Object> json) {
        try {
            UserDto userDto = convertMapToUserDto(json);
            userDto.setDocument(id);
            boolean userUpdated = userBusiness.updateUser(userDto);
            if (userUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("User updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("User update failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdateError"));
            }
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id) {
        try {
            boolean userDeleted = userBusiness.deleteUserById(id);
            if (userDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("User deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("User deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletionError"));
            }
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    private Map<String, Object> convertUserDtoToMap(UserDto userDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", userDto.getId());
        if (userDto.getDocument() != null) {
            map.put("document", userDto.getDocument());
        }
        map.put("password", userDto.getPassword());
        map.put("typeDocument", userDto.getTypeDocument());
        if (userDto.getRoleList() != null) {
            List<Map<String, Object>> roles = userDto.getRoleList().stream().map(role -> {
                Map<String, Object> roleMap = new HashMap<>();
                roleMap.put("id", role.getId());
                roleMap.put("name", role.getName());
                return roleMap;
            }).toList();
            map.put("roleList", roles);
        }
        if (userDto.getFk_id_person() != null) {
            map.put("fk_id_person", userDto.getFk_id_person());
        } else {
            map.put("fk_id_person", null);
        }
        return map;
    }

    private UserDto convertMapToUserDto(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        JSONObject dataObj = jsonObject.getJSONObject("data");
        UserDto userDto = new UserDto();
        userDto.setDocument(dataObj.getLong("document"));
        userDto.setPassword(dataObj.getString("password"));
        userDto.setTypeDocument(dataObj.getString("typeDocument"));
        if (dataObj.has("roleList")) {
            Object roleObj = dataObj.get("roleList");
            if (roleObj instanceof JSONArray rolesArray) {
                List<RoleDto> roles = new ArrayList<>();
                for (int i = 0; i < rolesArray.length(); i++) {
                    JSONObject rolesObj = rolesArray.getJSONObject(i);
                    roles.add(convertJSONObjectToRoleDto(rolesObj));
                }
                userDto.setRoleList(roles);
            }
        }
        if (dataObj.has("fk_id_person")) {
            JSONObject personObj = dataObj.getJSONObject("fk_id_person");
            PersonDto personDto = new PersonDto();
            personDto.setId(personObj.getLong("id"));
            userDto.setFk_id_person(personDto);
        }
        return userDto;
    }

    private RoleDto convertJSONObjectToRoleDto(JSONObject roleObj) {
        RoleDto roleDto = new RoleDto();
        if (roleObj.has("id")) {
            roleDto.setId(roleObj.getLong("id"));
        }
        if (roleObj.has("name")) {
            roleDto.setName(roleObj.getString("name"));
        }
        return roleDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }
}
