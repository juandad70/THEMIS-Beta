package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.ApprenticeBusiness;
import co.sena.edu.themis.Dto.ApprenticeDto;
import co.sena.edu.themis.Dto.PersonDto;
import co.sena.edu.themis.Dto.StudySheetDto;
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
@RequestMapping("/api/apprentice")
public class ApprenticeController {

    @Autowired
    private ApprenticeBusiness apprenticeBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllApprentices() {
        try {
            List<ApprenticeDto> apprentices = apprenticeBusiness.findAll(0, 10).getContent();
            List<Map<String, Object>> data = apprentices.stream()
                    .map(this::convertApprenticeDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Apprentices retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> getApprenticeById(@PathVariable Long id) {
        try {
            ApprenticeDto apprenticeDto = apprenticeBusiness.findById(id);
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Apprentice retrieved successfully", convertApprenticeDtoToMap(apprenticeDto), HttpStatus.OK));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createApprentice(@RequestBody Map<String, Object> json) {
        try {
            ApprenticeDto apprenticeDto = convertMapToApprenticeDto(json);
            boolean apprendiceCreated = apprenticeBusiness.createApprentice(apprenticeDto);
            if (apprendiceCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Apprentice created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Apprentice creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateApprendice(@PathVariable Long id, @RequestBody Map<String, Object> json) {
        try {
            ApprenticeDto apprenticeDto = convertMapToApprenticeDto(json);
            apprenticeDto.setId(id);
            boolean apprenticeUpdated = apprenticeBusiness.updateApprentice(apprenticeDto);
            if (apprenticeUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Apprentice updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Apprentice updated successfully", HttpStatus.INTERNAL_SERVER_ERROR, "UpdateError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteApprentice(@PathVariable Long id) {
        try {
            boolean apprenticeDeleted = apprenticeBusiness.deleteApprenticeById(id);
            if (apprenticeDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Apprentice delete successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Apprentice deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletionError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    private Map<String, Object> convertApprenticeDtoToMap(ApprenticeDto apprenticeDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", apprenticeDto.getId());
        if (apprenticeDto.getFk_id_person() != null) {
            map.put("fk_id_person", apprenticeDto.getFk_id_person());
        } else {
            map.put("fk_id_person", null);
        }

        if (apprenticeDto.getFk_id_study_sheet() != null) {
            map.put("fk_id_study_sheet", apprenticeDto.getFk_id_study_sheet());
        } else {
            map.put("fk_id_study_sheet", null);
        }
        return map;
    }

    private ApprenticeDto convertMapToApprenticeDto(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        JSONObject dataObj = jsonObject.getJSONObject("data");
        ApprenticeDto apprenticeDto = new ApprenticeDto();
        if (dataObj.has("fk_id_person")) {
            JSONObject personObj = dataObj.getJSONObject("fk_id_person");
            PersonDto personDto = new PersonDto();
            personDto.setId(personObj.getLong("id"));
            apprenticeDto.setFk_id_person(personDto);
        }
        if (dataObj.has("fk_id_study_sheet")) {
            JSONObject studySheetObj = dataObj.getJSONObject("fk_id_study_sheet");
            StudySheetDto studySheetDto = new StudySheetDto();
            studySheetDto.setId(studySheetObj.getLong("id"));
            apprenticeDto.setFk_id_study_sheet(studySheetDto);
        }
        return apprenticeDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException ce) {
        return ResponseEntity.status(ce.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(ce.getMessage(), ce.getHttpStatus(), ce.getTitle()));
    }
}
