package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.StudySheetBusiness;
import co.sena.edu.themis.Dto.StudySheetDto;
import co.sena.edu.themis.Util.Exception.CustomException;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/study-sheet")
public class StudySheetController {

    @Autowired
    private StudySheetBusiness studySheetBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllStudySheet() {
        try {
            List<StudySheetDto> studySheets = studySheetBusiness.findAll(0, 10).getContent();
            List<Map<String, Object>> data = studySheets.stream()
                    .map(this::convertStudySheetDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Study Sheet retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> getStudySheetById(@PathVariable Long id) {
        try {
            List<StudySheetDto> studySheetDtos = studySheetBusiness.findById(id);
            if (studySheetDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseHttpApi.responseHttpError("Study Sheet not found", HttpStatus.NOT_FOUND, "StudySheetNotFound"));
            }
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Study Sheet retrieved successfully", convertStudySheetDtoToMap(studySheetDtos.get(0)), HttpStatus.OK));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createStudySheet(@RequestBody Map<String, Object> json) {
        try {
            StudySheetDto studySheetDto = convertMapToStudySheetDto(json);
            boolean studySheetCreated = studySheetBusiness.createStudySheet(studySheetDto);
            if (studySheetCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Study Sheet created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Study Sheet creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateStudySheet(@PathVariable Long id, @RequestBody Map<String, Object> json) {
        try {
            StudySheetDto studySheetDto = convertMapToStudySheetDto(json);
            studySheetDto.setId(id);
            boolean studySheetUpdated = studySheetBusiness.updateStudySheet(studySheetDto);
            if (studySheetUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Study Sheet updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Study Sheet updated successfully", HttpStatus.INTERNAL_SERVER_ERROR, "UpdateError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteStudySheet(@PathVariable Long id) {
        try {
            boolean studySheetDeleted = studySheetBusiness.deleteStudySheetById(id);
            if (studySheetDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Study Sheet successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Study Sheet deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletionError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    private Map<String, Object> convertStudySheetDtoToMap(StudySheetDto studySheetDto){
        Map<String, Object> map = new HashMap<>();
        map.put("id", studySheetDto.getId());
        map.put("start_date", studySheetDto.getStart_date());
        map.put("end_date", studySheetDto.getEnd_date());
        map.put("number_students", studySheetDto.getNumber_students());
        if (studySheetDto.getFk_id_person() != null) {
            map.put("fk_id_person", studySheetDto.getFk_id_person());
        } else {
            map.put("fk_id_person", null);
        }

        if (studySheetDto.getFk_id_program() != null) {
            map.put("fk_id_program", studySheetDto.getFk_id_program());
        } else {
            map.put("fk_id_program", null);
        }

        return map;
    }

    private StudySheetDto convertMapToStudySheetDto(Map<String, Object> map) {
        StudySheetDto studySheetDto = new StudySheetDto();
        studySheetDto.setStart_date((Date) map.get("start_date"));
        studySheetDto.setEnd_date((Date) map.get("end_date"));
        studySheetDto.setNumber_students((int) map.get("number_students"));
        return studySheetDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }

}
