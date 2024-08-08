package co.sena.edu.themis.Controller;


import co.sena.edu.themis.Dto.NoveltyDTO;

import co.sena.edu.themis.Service.NoveltyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/novelties")
public class NoveltyController {

    @Autowired
    private NoveltyService noveltyService;

    @PostMapping
    public ResponseEntity<NoveltyDTO> createNovelty(@RequestBody NoveltyDTO noveltyDTO) {
        return new ResponseEntity<>(noveltyService.createNovelty(noveltyDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoveltyDTO> updateNovelty(@PathVariable Long id, @RequestBody NoveltyDTO noveltyDTO) {
        return ResponseEntity.ok(noveltyService.updateNovelty(id, noveltyDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNovelty(@PathVariable Long id) {
        noveltyService.deleteNovelty(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoveltyDTO> getNovelty(@PathVariable Long id) {
        return ResponseEntity.ok(noveltyService.getNovelty(id));
    }

    @GetMapping
    public ResponseEntity<List<NoveltyDTO>> getAllNovelties() {
        return ResponseEntity.ok(noveltyService.getAllNovelties());
    }
}