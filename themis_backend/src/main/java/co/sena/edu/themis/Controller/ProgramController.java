package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Entity.Program;
import co.sena.edu.themis.Service.ProgramService;
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
    private ProgramService programService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllPrograms() {
        List<Program> programs = programService.findAll();
        List<Map<String, Object>> data = programs.stream()
                .map(this::convertProgramToMap)
                .toList();
        return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Programs retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProgramById(@PathVariable Long id) {
        return programService.findById(id)
                .map(program -> ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Program retrieved successfully", convertProgramToMap(program), HttpStatus.OK)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseHttpApi.responseHttpError("Program not found", HttpStatus.NOT_FOUND, "ProgramNotFound")));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createProgram(@RequestBody Map<String, Object> requestBody) {
        try {
            String programName = (String) requestBody.get("programName");
            if (programService.existsByProgramName(programName)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(ResponseHttpApi.responseHttpError("Program already exists", HttpStatus.CONFLICT, "ProgramAlreadyExists"));
            }

            Program program = convertMapToProgram(requestBody);
            Program createdProgram = programService.save(program);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ResponseHttpApi.responseHttpPost("Program created successfully", HttpStatus.CREATED));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateProgram(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        try {
            Program program = convertMapToProgram(requestBody);
            program.setId(id);
            Program updatedProgram = programService.save(program);
            return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Program updated successfully", HttpStatus.OK));
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteProgram(@PathVariable Long id) {
        programService.deleteById(id);
        return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Program deleted successfully", HttpStatus.OK));
    }

    private Map<String, Object> convertProgramToMap(Program program) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", program.getId());
        map.put("programName", program.getProgramName());
        map.put("description", program.getDescription());
        map.put("status", program.getStatus());
        if (program.getFk_id_coordination() != null) {
            map.put("fk_id_coordination", program.getFk_id_coordination().getId());
        } else {
            map.put("fk_id_coordination", null);
        }
        return map;
    }

    private Program convertMapToProgram(Map<String, Object> map) {
        Program program = new Program();
        program.setProgramName((String) map.get("programName"));
        program.setDescription((String) map.get("description"));
        program.setStatus((String) map.get("status"));
        // Aquí podrías agregar la lógica para asignar fk_id_coordination si se necesita
        return program;
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
