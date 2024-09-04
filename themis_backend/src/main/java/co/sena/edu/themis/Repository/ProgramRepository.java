package co.sena.edu.themis.Repository;

import co.sena.edu.themis.Entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {
    boolean existsByProgramName(String programName); // Nombre correcto del método
}
