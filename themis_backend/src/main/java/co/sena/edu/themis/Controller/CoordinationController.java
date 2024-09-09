package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.CoordinationBusiness;
import co.sena.edu.themis.Dto.CoordinationDto;
import co.sena.edu.themis.Util.Exception.CustomException;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/coordination")
public class CoordinationController {

    @Autowired
    private CoordinationBusiness coordinationBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllCoordinations() {
        try {
            List<CoordinationDto> coordinations = coordinationBusiness.findAll(0,10).getContent();
            List<Map<String, Object>> data = coordinations.stream()
                    .map(this::convertCoordinationDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Coordinations retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> getCoordinationById(@PathVariable Long id) {
        try {
            List<CoordinationDto> coordinationDtos = coordinationBusiness.findById(id);
            if (coordinationDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseHttpApi.responseHttpError("Coordination not found", HttpStatus.NOT_FOUND, "CoordinationNotFound"));
            }
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Coordination retrieved successfully", convertCoordinationDtoToMap(coordinationDtos.get(0)), HttpStatus.OK));
        } catch (CustomException customE){
            return handleCustomException(customE);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createCoordination(@RequestBody Map<String, Object> requestBody) {
        try {
            CoordinationDto coordinationDto = convertMapToCoordinationDto(requestBody);
            boolean created = coordinationBusiness.createCoordination(coordinationDto);
            if (created) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Coordination created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError( "Coordination created failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateCoordination(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        try {
            CoordinationDto coordinationDto = convertMapToCoordinationDto(requestBody);
            coordinationDto.setId(id);
            boolean coordinationUpdated = coordinationBusiness.updateCoordination(coordinationDto);
            if (coordinationUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Coordination updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Coordination updated failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdatedFailed"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteCoordinationById(@PathVariable Long id) {
        try {
            boolean coordinationDeleted = coordinationBusiness.deleteCoordinationById(id);
            if (coordinationDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Coordination deleted sucessfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Coordination deleted failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletedFailed"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }
    


    private Map<String, Object> convertCoordinationDtoToMap(CoordinationDto coordinationDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", coordinationDto.getId());
        map.put("name", coordinationDto.getName());
        if (coordinationDto.getFk_id_committee() != null) {
            map.put("fk_id_committee", coordinationDto.getFk_id_committee());
        } else {
            map.put("fk_id_committee", null);
        }
        return map;
    }

    private CoordinationDto convertMapToCoordinationDto(Map<String, Object> map) {
        CoordinationDto coordinationDto = new CoordinationDto();
        coordinationDto.setName((String) map.get("name"));
        return coordinationDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }
}
