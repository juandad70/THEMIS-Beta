package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.NoveltyBusiness;

import co.sena.edu.themis.Dto.NoveltyDto;
import co.sena.edu.themis.Util.Http.ResponseHttpApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/novelties")
public class NoveltyController {

    @Autowired
    private NoveltyBusiness noveltyBusiness;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllNovelties() {
        List<NoveltyDto> novelties = noveltyBusiness.findAll();
        return ResponseEntity.ok(ResponseHttpApi.responseHttpFind("Novelties retrieved successfully", novelties, HttpStatus.OK, 1, (long) novelties.size()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getNoveltyById(@PathVariable Long id) {
        NoveltyDto novelty = noveltyBusiness.findById(id);
        if (novelty != null) {
            return ResponseEntity.ok(ResponseHttpApi.responseHttpFindById("Novelty retrieved successfully", novelty, HttpStatus.OK));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseHttpApi.responseHttpError("Novelty not found", HttpStatus.NOT_FOUND, "NoveltyNotFound"));
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createNovelty(@RequestBody NoveltyDto noveltyDTO) {
        NoveltyDto createdNovelty = noveltyBusiness.save(noveltyDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseHttpApi.responseHttpPost("Novelty created successfully", HttpStatus.CREATED));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateNovelty(@PathVariable Long id, @RequestBody NoveltyDto noveltyDTO) {
        noveltyDTO.setId(id);
        NoveltyDto updatedNovelty = noveltyBusiness.save(noveltyDTO);
        return ResponseEntity.ok(ResponseHttpApi.responseHttpPut("Novelty updated successfully", HttpStatus.OK));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteNovelty(@PathVariable Long id) {
        noveltyBusiness.deleteById(id);
        return ResponseEntity.ok(ResponseHttpApi.responseHttpDelete("Novelty deleted successfully", HttpStatus.OK));
    }
}