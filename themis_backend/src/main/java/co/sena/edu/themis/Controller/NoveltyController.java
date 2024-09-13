package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.NoveltyBusiness;

import co.sena.edu.themis.Dto.NoveltyDto;
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
@RequestMapping("/api/novelties")
public class NoveltyController {

    @Autowired
    private NoveltyBusiness noveltyBusiness;

    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> getAllNovelties() {
        try {
            List<NoveltyDto> novelties = noveltyBusiness.findAll(0, 10).getContent();
            List<Map<String, Object>> data = novelties.stream()
                    .map(this::convertNoveltyDtoToMap)
                    .toList();
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Novelties retrieved successfully", data, HttpStatus.OK, 1, (long) data.size()));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }


    @GetMapping("/all/{id}")
    public ResponseEntity<Map<String, Object>> getNoveltyById(@PathVariable Long id) {
        try {
            List<NoveltyDto> noveltyDtos = noveltyBusiness.findById(id);
            if (noveltyDtos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ResponseHttpApi.responseHttpError("Novelty not found", HttpStatus.NOT_FOUND, "NoveltyNotFound"));
            }
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Novelty retrieved successfully", convertNoveltyDtoToMap(noveltyDtos.get(0)), HttpStatus.OK));
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createNovelty(@RequestBody Map<String, Object> requestBody) {
        try {
            NoveltyDto noveltyDto = convertMapToNoveltyDto(requestBody);
            boolean noveltyCreated = noveltyBusiness.createNovelty(noveltyDto);
            if (noveltyCreated) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ResponseHttpApi.responseHttpPost("Novelty created successfully", HttpStatus.CREATED));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Novelty creation failed", HttpStatus.INTERNAL_SERVER_ERROR, "CreationError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Map<String, Object>> updateNovelty(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        try {
            NoveltyDto noveltyDto = convertMapToNoveltyDto(requestBody);
            noveltyDto.setId(id);
            boolean noveltyUpdated = noveltyBusiness.updateNovelty(noveltyDto);
            if (noveltyUpdated) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Novelty updated successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Novelty update failed", HttpStatus.INTERNAL_SERVER_ERROR, "UpdateError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteNovelty(@PathVariable Long id) {
        try {
            boolean noveltyDeleted = noveltyBusiness.deleteNoveltyById(id);
            if (noveltyDeleted) {
                return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Novelty deleted successfully", HttpStatus.OK));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ResponseHttpApi.responseHttpError("Novelty deletion failed", HttpStatus.INTERNAL_SERVER_ERROR, "DeletionError"));
            }
        } catch (CustomException customE) {
            return handleCustomException(customE);
        }
    }


    private Map<String, Object> convertNoveltyDtoToMap(NoveltyDto noveltyDto) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", noveltyDto.getId());
        map.put("nov_date", noveltyDto.getNov_date());
        map.put("observation", noveltyDto.getObservation());
        map.put("status", noveltyDto.getStatus());
        map.put("image", noveltyDto.getImage());

        //Mapeo de llave foranea de fk_id_novelty_type
        if (noveltyDto.getFk_id_novelty_type() != null) {
            map.put("fk_id_novelty_type", noveltyDto.getFk_id_novelty_type());
        } else {
            map.put("fk_id_novelty_type", null);
        }

        //Mapeo de llave foranea de fk_id_person
        if (noveltyDto.getFk_id_person() != null) {
            map.put("fk_id_person", noveltyDto.getFk_id_person());
        } else {
            map.put("fk_id_person", null);
        }

        //Mapeo de llave foranea de fk_id_coordination
        if (noveltyDto.getFk_id_coordination() != null) {
            map.put("fk_id_coordination", noveltyDto.getFk_id_coordination());
        } else  {
            map.put("fk_id_coordination", null);
        }

        return map;
    }

    private NoveltyDto convertMapToNoveltyDto(Map<String, Object> map) {
        NoveltyDto noveltyDto = new NoveltyDto();
        noveltyDto.setNov_date((Date) map.get("nov_date"));
        noveltyDto.setObservation((String) map.get("observation"));
        noveltyDto.setStatus((String) map.get("status"));
        return noveltyDto;
    }

    private ResponseEntity<Map<String, Object>> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getHttpStatus())
                .body(ResponseHttpApi.responseHttpError(e.getMessage(), e.getHttpStatus(), e.getTitle()));
    }


}