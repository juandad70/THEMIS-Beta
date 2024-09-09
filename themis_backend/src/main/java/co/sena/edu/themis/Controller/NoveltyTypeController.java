package co.sena.edu.themis.Controller;



import co.sena.edu.themis.Business.NoveltyTypeBusiness;

import co.sena.edu.themis.Dto.NoveltyTypeDto;
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
@RequestMapping("/api/novelty-types")
public class NoveltyTypeController {

    @Autowired
    private NoveltyTypeBusiness noveltyTypeBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllNoveltyTypes() {
        try {
            List<NoveltyTypeDto> noveltyTypes = noveltyTypeBusiness.findAll(0, 10).getContent();
            List<Map<String, Object>> data = noveltyTypes.stream()
                    .map(this::convertNoveltyTypeDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Novelty types retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> getNoveltyTypeById(@PathVariable Long id) {
        try {
            List<NoveltyTypeDto> noveltyTypeDtos = noveltyTypeBusiness.findById(id);
            if (noveltyTypeDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseHttpApi.responseHttpError("Novelty type not found", HttpStatus.NOT_FOUND, "NoveltyTypeNotFound"));
            }
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Novelty Type retrieved successfully", convertNoveltyTypeDtoToMap(noveltyTypeDtos.get(0)), HttpStatus.OK));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createNoveltyType(@RequestBody Map<String, Object> json) {
        try {
            NoveltyTypeDto noveltyTypeDto = convertMapToNoveltyTypeDto(json);
            boolean noveltyTypeCreated = noveltyTypeBusiness.createNoveltyType(noveltyTypeDto);
            if (noveltyTypeCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Novelty type created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Novelty type creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateNoveltyType(@PathVariable Long id, @RequestBody Map<String, Object> json) {
        try {
            NoveltyTypeDto noveltyTypeDto = convertMapToNoveltyTypeDto(json);
            noveltyTypeDto.setId(id);
            boolean noveltyTypeCreated = noveltyTypeBusiness.updateNoveltyType(noveltyTypeDto);
            if (noveltyTypeCreated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Novelty type created successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Novelty type update failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdateError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteNoveltyTypeById(@PathVariable Long id) {
        try {
            boolean noveltyTypeDeleted = noveltyTypeBusiness.deleteNoveltyTypeById(id);
            if (noveltyTypeDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Novelty type deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Novelty type deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletionError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }


    private Map<String, Object> convertNoveltyTypeDtoToMap(NoveltyTypeDto noveltyTypeDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", noveltyTypeDto.getId());
        map.put("novel_date", noveltyTypeDto.getNovel_date());
        map.put("novel_type", noveltyTypeDto.getNovel_type());
        map.put("novel_state", noveltyTypeDto.getNovel_state());
        map.put("sofia_certainty", noveltyTypeDto.getSofia_certainty());
        map.put("description", noveltyTypeDto.getDescription());
        return map;
    }

    private NoveltyTypeDto convertMapToNoveltyTypeDto(Map<String, Object> map) {
        NoveltyTypeDto noveltyTypeDto = new NoveltyTypeDto();
        noveltyTypeDto.setNovel_date((Date) map.get("novel_date"));
        noveltyTypeDto.setNovel_type((String) map.get("novel_type"));
        noveltyTypeDto.setNovel_state((String) map.get("novel_state"));
        noveltyTypeDto.setSofia_certainty((String) map.get("sofia_certainty"));
        noveltyTypeDto.setDescription((String) map.get("description"));
        return noveltyTypeDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(),e.getTitle()));
    }
}