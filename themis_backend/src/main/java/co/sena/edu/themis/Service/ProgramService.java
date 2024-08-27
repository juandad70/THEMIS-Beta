package co.sena.edu.themis.Service;

import co.sena.edu.themis.Entity.Program;
import co.sena.edu.themis.Repository.ProgramRepository;
import co.sena.edu.themis.Service.Dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgramService implements Idao<Program, Long> {

    @Autowired
    private ProgramRepository programRepository;

    @Override
    public List<Program> findAll() {
        return programRepository.findAll();
    }

    @Override
    public Program getById(Long id) {
        return programRepository.getById(id);
    }

    @Override
    public void save(Program program) {
        programRepository.save(program);
    }

    @Override
    public void deleteById(Long id) {
        programRepository.deleteById(id);
    }

    @Override
    public Page<Program> findAll(Pageable pageable) {
        return this.programRepository.findAll(pageable);
    }
}
