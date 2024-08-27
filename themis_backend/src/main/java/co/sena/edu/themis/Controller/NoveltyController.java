package co.sena.edu.themis.Controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NoveltyController {

    @GetMapping("/obtainNov")
    public String obtainNovelty() {
        return "Datos obtenidos con exito";
    }

    @GetMapping("/obtainNov/{id}")
    public String obtainNoveltyById() {
        return "Datos obtenidos con exito";
    }

    @PostMapping("/registerNov")
    public String registerNovelty() {
        return "Novedad registrada con exito";
    }

    @PostMapping("/createNov")
    public String createNovelty() {
        return "Novedad creada con exito";
    }

    @PutMapping("/updateNov/{id}")
    public String updateNovelty(@PathVariable int id) {
        return "Novedad actualizada con exito";
    }

    @DeleteMapping("/deleteNov/{id}")
    public String deleteNovelty(@PathVariable int id) {
        return "Novedad eliminada con exito";
    }
}
