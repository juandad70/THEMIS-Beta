package co.sena.edu.themis.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApplicationLetterController {

    @GetMapping("/obtainAppli")
    public String obtainApplicationLetter() {
        return "Carta obtenida con exito";
    }

    @GetMapping("/obtainAppli/{id}")
    public String obtainApplicationLetterById() {
        return "Dato obtenido con exito";
    }

    @PostMapping("/registerAppli")
    public String registerApplicationLetter() {
        return "Carta registrada con exito";
    }

    @PostMapping("/createAppli")
    public String createApplicationLetter() {
        return "Carta creada con exito";
    }

    @DeleteMapping("/deleteAppli/{id}")
    public String deleteApplicationLetter(@PathVariable int id) {
        return "Carta eliminada con exito";
    }
}