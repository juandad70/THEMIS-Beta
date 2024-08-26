package co.sena.edu.themis.repository;

import co.sena.edu.themis.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
