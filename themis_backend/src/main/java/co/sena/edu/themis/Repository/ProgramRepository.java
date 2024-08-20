package co.sena.edu.themis.Repository;

import co.sena.edu.themis.Entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}
