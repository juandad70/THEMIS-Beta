package co.sena.edu.themis.Service;

import co.sena.edu.themis.Entity.Program;
import co.sena.edu.themis.Repository.ProgramRepository;
import co.sena.edu.themis.Service.Dao.IProgramDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService implements IProgramDao {

    @Autowired
    private ProgramRepository programRepository;

    @Override
    public List<Program> findAll() {
        return programRepository.findAll();
    }

    @Override
    public Optional<Program> findById(Long id) {
        return programRepository.findById(id);
    }

    @Override
    public Program save(Program program) {
        return programRepository.save(program);
    }

    @Override
    public void deleteById(Long id) {
        programRepository.deleteById(id);
    }

    @Override
    public boolean existsByProgramName(String programName) {
        return programRepository.existsByProgramName(programName); // Nombre correcto del método
    }
}
