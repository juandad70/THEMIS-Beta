package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.ProgramBusiness;
import co.sena.edu.themis.Dto.ProgramDto;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
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

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPrograms(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        var programs = programBusiness.findAll(page, size);
        List<ProgramDto> programDtoList = programs.getContent();
        return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Programs retrieved successfully", programDtoList, HttpStatus.OK, programs.getNumber(), (long) programDtoList.size()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProgramById(@PathVariable Long id) {
        try {
            List<ProgramDto> programDtoList = programBusiness.findById(id);
            if (programDtoList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseHttpApi.responseHttpError("Program not found", HttpStatus.NOT_FOUND, "ProgramNotFound"));
            } else {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Program retrieved successfully", programDtoList.get(0), HttpStatus.OK));
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProgram(@RequestBody ProgramDto programDto) {
        try {
            boolean isCreated = programBusiness.createProgram(programDto);
            if (isCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Program created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Failed to create program", HttpStatus.INTERNAL_SERVER_ERROR, "ProgramCreationFailed"));
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProgram(@PathVariable Long id, @RequestBody ProgramDto programDto) {
        try {
            programDto.setId(id);
            boolean isUpdated = programBusiness.updateProgram(programDto);
            if (isUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Program updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Failed to update program", HttpStatus.INTERNAL_SERVER_ERROR, "ProgramUpdateFailed"));
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProgram(@PathVariable Long id) {
        try {
            boolean isDeleted = programBusiness.deleteProgramById(id);
            if (isDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Program deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Failed to delete program", HttpStatus.INTERNAL_SERVER_ERROR, "ProgramDeletionFailed"));
            }
        } catch (Exception e) {
            return handleException(e);
        }
    }

    private ResponseEntity<Map<String, Object>> handleException(Exception e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", "error");
        if (e.getMessage().contains("Unique index or primary key violation")) {
            errorResponse.put("message", "El registro ya existe.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } else {
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
