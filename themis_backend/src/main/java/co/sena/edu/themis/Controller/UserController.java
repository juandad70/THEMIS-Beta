package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.UserBusiness;
import co.sena.edu.themis.Dto.ProgramDto;
import co.sena.edu.themis.Dto.UserDto;
import co.sena.edu.themis.Util.Exception.CustomException;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            List<UserDto> userDtos = userBusiness.findById(id);
            if (userDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseHttpApi.responseHttpError("User not found", HttpStatus.NOT_FOUND, "UserNotFound"));
            }
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("User retrieved successfully", convertUserDtoToMap(userDtos.get(0)), HttpStatus.OK));
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
        if (userDto.getDocument() != null) {
            map.put("document", userDto.getDocument());
        } else {
            map.put("document", ""); // O un valor por defecto adecuado
        }
        map.put("password", userDto.getPassword());
        map.put("type_document", userDto.getType_document());
        return map;
    }


    private UserDto convertMapToUserDto(Map<String, Object> map) {
        UserDto userDto = new UserDto();

        // Verificar y convertir el campo 'document' al tipo Long
        Object documentObj = map.get("document");
        if (documentObj != null) {
            if (documentObj instanceof Integer) {
                userDto.setDocument(((Integer) documentObj).longValue()); // Convertir Integer a Long
            } else if (documentObj instanceof Long) {
                userDto.setDocument((Long) documentObj); // Ya es Long
            }
        }
        userDto.setPassword((String) map.get("password"));
        userDto.setType_document((String) map.get("type_document"));
        return userDto;
    }


    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }
}
