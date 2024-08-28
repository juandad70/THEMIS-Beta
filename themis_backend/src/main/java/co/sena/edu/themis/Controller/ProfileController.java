package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Entity.Person;
import co.sena.edu.themis.Entity.Student;
import co.sena.edu.themis.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ProfileController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/profile/{id}")
    public ResponseEntity<Student> getProfile(@PathVariable Long id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/profile/{id}")
    public ResponseEntity<Student> updateProfile(@PathVariable Long id, @RequestBody Person person) {
        return studentRepository.findById(id)
                .map(student -> {
                    // Actualiza los campos de la entidad Person en el objeto Student
                    student.getPerson().setEmail(person.getEmail());
                    student.getPerson().setPhone(person.getPhone());

                    // Guarda los cambios en la base de datos
                    Student updatedStudent = studentRepository.save(student);
                    return ResponseEntity.ok(updatedStudent);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
