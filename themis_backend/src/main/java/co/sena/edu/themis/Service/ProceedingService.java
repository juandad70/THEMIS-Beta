package co.sena.edu.themis.Service;

import co.sena.edu.themis.Entity.Proceeding;
import co.sena.edu.themis.Repository.ProceedingRepository;
import co.sena.edu.themis.Service.Dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProceedingService implements Idao<Proceeding, Long> {

    @Autowired
    private ProceedingRepository proceedingRepository;

    @Override
    public List<Proceeding> findAll() {
        return proceedingRepository.findAll();
    }

    @Override
    public Proceeding getById(Long id) {
        return proceedingRepository.getById(id);
    }

    @Override
    public void save(Proceeding proceeding) {
        proceedingRepository.save(proceeding);
    }

    @Override
    public void deleteById(Long id) {
        proceedingRepository.deleteById(id);
    }

    @Override
    public Page<Proceeding> findAll(Pageable pageable) {
        return this.proceedingRepository.findAll(pageable);
    }
}
