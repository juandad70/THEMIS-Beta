package co.sena.edu.themis.Controller;



import co.sena.edu.themis.Business.NoveltyTypeBusiness;

import co.sena.edu.themis.Dto.NoveltyTypeDto;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/novelty-types")
public class NoveltyTypeController {

    @Autowired
    private NoveltyTypeBusiness noveltyTypeBusiness;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllNoveltyTypes() {
        List<NoveltyTypeDto> noveltyTypes = noveltyTypeBusiness.findAll();
        return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Novelty types retrieved successfully", noveltyTypes, HttpStatus.OK, 1, (long) noveltyTypes.size()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getNoveltyTypeById(@PathVariable Long id) {
        NoveltyTypeDto noveltyType = noveltyTypeBusiness.findById(id);
        if (noveltyType != null) {
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Novelty type retrieved successfully", noveltyType, HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseHttpApi.responseHttpError("Novelty type not found", HttpStatus.NOT_FOUND, "NoveltyTypeNotFound"));
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createNoveltyType(@RequestBody NoveltyTypeDto noveltyTypeDTO) {
        NoveltyTypeDto createdNoveltyType = noveltyTypeBusiness.save(noveltyTypeDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseHttpApi.responseHttpPost("Novelty type created successfully", HttpStatus.CREATED));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateNoveltyType(@PathVariable Long id, @RequestBody NoveltyTypeDto noveltyTypeDTO) {
        noveltyTypeDTO.setId(id);
        NoveltyTypeDto updatedNoveltyType = noveltyTypeBusiness.save(noveltyTypeDTO);
        return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Novelty type updated successfully", HttpStatus.OK));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteNoveltyType(@PathVariable Long id) {
        noveltyTypeBusiness.deleteById(id);
        return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Novelty type deleted successfully", HttpStatus.OK));
    }
}