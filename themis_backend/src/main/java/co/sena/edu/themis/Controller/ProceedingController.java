package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.ProceedingBusiness;
import co.sena.edu.themis.Dto.ProceedingDto;
import co.sena.edu.themis.Util.Exception.CustomException;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/proceeding")
public class ProceedingController {

    @Autowired
    private ProceedingBusiness proceedingBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllProceedings() {
        try {
            List<ProceedingDto> proceedings = proceedingBusiness.findAll(0, 10).getContent();
            List<Map<String, Object>> data = proceedings.stream()
                    .map(this::convertProceedingDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Proceedings retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> getAllProceedingById(@PathVariable Long id) {
        try {
            ProceedingDto proceedingDtos = proceedingBusiness.findById(id);
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Proceeding retrieved successfully", convertProceedingDtoToMap(proceedingDtos), HttpStatus.OK));
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @GetMapping("/create")
    public ResponseEntity<Map<String, Object>> createProceeding(@RequestBody Map<String, Object> json) {
        try {
            ProceedingDto proceedingDto = convertMapToProceedingDto(json);
            boolean proceedingCreated = proceedingBusiness.createProceeding(proceedingDto);
            if (proceedingCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Proceeding created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Proceeding creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateProceeding(@PathVariable Long id, @RequestBody Map<String, Object> json) {
        try {
            ProceedingDto proceedingDto = convertMapToProceedingDto(json);
            proceedingDto.setId(id);
            boolean proceedingUpdated = proceedingBusiness.updateProceeding(proceedingDto);
            if (proceedingUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Proceeding updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Proceeding update failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdateError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteProceeding(@PathVariable Long id) {
        try {
            boolean proceedingDeleted = proceedingBusiness.deleteProceedingById(id);
            if (proceedingDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Proceeding deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Proceeding deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletionError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }


    private Map<String, Object> convertProceedingDtoToMap(ProceedingDto proceedingDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", proceedingDto.getId());
        map.put("name", proceedingDto.getName());
        map.put("proceedingFile", proceedingDto.getProceedingFile());
        if (proceedingDto.getFk_id_nov_type() != null) {
            map.put("fk_id_nov_type", proceedingDto.getFk_id_nov_type());
        } else {
            map.put("fk_id_nov_type", null);
        }
        return map;
    }

    private ProceedingDto convertMapToProceedingDto(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        JSONObject dataObj = jsonObject.getJSONObject("data");
        ProceedingDto proceedingDto = new ProceedingDto();
        proceedingDto.setId(dataObj.getLong("id"));
        proceedingDto.setName(dataObj.getString("name"));
        proceedingDto.setProceedingFile(dataObj.getString("proceedingFile"));
        return proceedingDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }
}
