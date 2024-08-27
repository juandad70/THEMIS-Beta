package co.sena.edu.themis.Repository;

import co.sena.edu.themis.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
