package co.sena.edu.themis.Service.Dao;

import co.sena.edu.themis.Entity.Program;
import java.util.List;
import java.util.Optional;

public interface IProgramDao {
    List<Program> findAll();
    Optional<Program> findById(Long id);
    Program save(Program program);
    void deleteById(Long id);
    boolean existsByProgramName(String programName); // Nombre correcto del método
}
