package co.sena.edu.themis.Controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class StudentController {

    @GetMapping("/obtainStud")
    public String obtainStudent() {
        return "Datos obtenidos con exito";
    }

    @GetMapping("/obtainStud/{id}")
    public String obtainStudentById() {
        return "Datos obtenidos con exito";
    }


    @PutMapping("/updateStud/{id}")
    public String updateStudent(@PathVariable int id) {
        return "Perfil actualizado con exito";
    }

    @DeleteMapping("/deleteStud/{id}")
    public String deleteStudent(@PathVariable int id) {
        return " eliminado con exito";
    }
}
