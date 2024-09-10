package co.sena.edu.themis.Controller;


import co.sena.edu.themis.Business.RoleBusiness;
import co.sena.edu.themis.Dto.RoleDto;
import co.sena.edu.themis.Util.Exception.CustomException;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.hibernate.collection.spi.MapSemantics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleBusiness roleBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllRoles() {
        try {
            List<RoleDto> roles = roleBusiness.findAll(0, 10).getContent();
            List<Map<String, Object>> data = roles.stream()
                    .map(this::convertRoleDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Roles retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> getRoleById(@PathVariable Long id) {
        try {
            List<RoleDto> roleDtos = roleBusiness.findById(id);
            if (roleDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseHttpApi.responseHttpError("Role not found", HttpStatus.NOT_FOUND, "RoleNotFound"));
            }
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Role retrieved successfully", convertRoleDtoToMap(roleDtos.get(0)), HttpStatus.OK));
        } catch (CustomException customE)  {
            return handleCustomException(customE);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createRole(@RequestBody Map<String, Object> json) {
        try {
            RoleDto roleDto = convertMapToRoleDto(json);
            boolean roleCreated = roleBusiness.createRole(roleDto);
            if (roleCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Role created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Role creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateRole(@PathVariable Long id, @RequestBody Map<String, Object> json) {
        try {
            RoleDto roleDto = convertMapToRoleDto(json);
            roleDto.setId(id);
            boolean roleUpdated = roleBusiness.updateRole(roleDto);
            if (roleUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Role updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Role update failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdateError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteRole(@PathVariable Long id) {
        try {
            boolean roleDeleted = roleBusiness.deleteRoleById(id);
            if (roleDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Role deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Role deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletionError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }


    private Map<String, Object> convertRoleDtoToMap(RoleDto roleDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", roleDto.getId());
        map.put("name", roleDto.getName());
        return map;
    }

    private RoleDto convertMapToRoleDto(Map<String, Object> map) {
        RoleDto roleDto = new RoleDto();
        roleDto.setName((String) map.get("name"));
        return roleDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }
}
