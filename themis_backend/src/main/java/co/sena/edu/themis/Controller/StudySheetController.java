package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.StudySheetBusiness;
import co.sena.edu.themis.Dto.PersonDto;
import co.sena.edu.themis.Dto.ProgramDto;
import co.sena.edu.themis.Dto.StudySheetDto;
import co.sena.edu.themis.Util.Exception.CustomException;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            StudySheetDto studySheetDtos = studySheetBusiness.findById(id);
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Study Sheet retrieved successfully", convertStudySheetDtoToMap(studySheetDtos), HttpStatus.OK));
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
        map.put("numberSheet", studySheetDto.getNumberSheet());
        map.put("startDate", studySheetDto.getStartDate());
        map.put("endDate", studySheetDto.getEndDate());
        map.put("numberStudents", studySheetDto.getNumberStudents());
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
        JSONObject jsonObject = new JSONObject(map);
        JSONObject dataObj = jsonObject.getJSONObject("data");
        StudySheetDto studySheetDto = new StudySheetDto();
        studySheetDto.setNumberSheet(dataObj.getLong("numberSheet"));
        try {
            String studySheetStr = dataObj.getString("startDate");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date starDate = dateFormat.parse(studySheetStr);
            studySheetDto.setStartDate(starDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            String studySheetStr = dataObj.getString("endDate");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date endDate = dateFormat.parse(studySheetStr);
            studySheetDto.setEndDate(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dataObj.has("fk_id_person")) {
            JSONObject personObj = dataObj.getJSONObject("fk_id_person");
            PersonDto personDto = new PersonDto();
            personDto.setId(personObj.getLong("id"));
            studySheetDto.setFk_id_person(personDto);
        }

        if (dataObj.has("fk_id_program")){
            JSONObject programObj = dataObj.getJSONObject("fk_id_program");
            ProgramDto programDto = new ProgramDto();
            programDto.setId(programObj.getLong("id"));
            studySheetDto.setFk_id_program(programDto);
        }

        studySheetDto.setNumberStudents(dataObj.getInt("numberStudents"));
        return studySheetDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }

}
