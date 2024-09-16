package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.ProgramBusiness;
import co.sena.edu.themis.Dto.ProgramDto;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import co.sena.edu.themis.Util.Exception.CustomException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    @Autowired
    private ProgramBusiness programBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllPrograms() {
        try {
            List<ProgramDto> programs = programBusiness.findAll(0, 10).getContent();
            List<Map<String, Object>> data = programs.stream()
                    .map(this::convertProgramDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Programs retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProgramById(@PathVariable Long id) {
        try {
            ProgramDto programDto = programBusiness.findById(id);
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Program retrieved successfully", convertProgramDtoToMap(programDto), HttpStatus.OK));
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }


    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createProgram(@RequestBody Map<String, Object> requestBody) {
        try {
            ProgramDto programDto = convertMapToProgramDto(requestBody);
            boolean created = programBusiness.createProgram(programDto);
            if (created) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Program created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Program creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateProgram(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        try {
            ProgramDto programDto = convertMapToProgramDto(requestBody);
            programDto.setId(id);
            boolean updated = programBusiness.updateProgram(programDto);
            if (updated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Program updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Program update failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdateError"));
            }
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteProgram(@PathVariable Long id) {
        try {
            boolean deleted = programBusiness.deleteProgramById(id);
            if (deleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Program deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Program deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletionError"));
            }
        } catch (CustomException e) {
            return handleCustomException(e);
        }
    }


    private Map<String, Object> convertProgramDtoToMap(ProgramDto programDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", programDto.getId());
        map.put("programName", programDto.getProgramName());
        map.put("description", programDto.getDescription());
        map.put("status", programDto.getStatus());
        if (programDto.getFk_id_coordination() != null) {
            map.put("fk_id_coordination", programDto.getFk_id_coordination());
        } else {
            map.put("fk_id_coordination", null);
        }
        return map;
    }

    private ProgramDto convertMapToProgramDto(Map<String, Object> map) {
        JSONObject jsonObject = new JSONObject(map);
        JSONObject dataObj = jsonObject.getJSONObject("data");
        ProgramDto programDto = new ProgramDto();
        programDto.setId(dataObj.getLong("id"));
        programDto.setProgramName(dataObj.getString("programName"));
        programDto.setDescription(dataObj.getString("description"));
        programDto.setStatus(dataObj.getString("status"));
        return programDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }
}
