package co.sena.edu.themis.controller;

import co.sena.edu.themis.entity.Person;
import co.sena.edu.themis.entity.Student;
import co.sena.edu.themis.repository.StudentRepository;
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
                    student.getPerson().setEmail(person.getEmail());
                    student.getPerson().setPhone(person.getPhone());
                    Student updatedStudent = studentRepository.save(student);
                    return ResponseEntity.ok(updatedStudent);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
