package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.CoordinationBusiness;
import co.sena.edu.themis.Dto.CoordinationDto;
import co.sena.edu.themis.Util.Exception.CustomException;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
