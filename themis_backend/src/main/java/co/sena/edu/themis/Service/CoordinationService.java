package co.sena.edu.themis.Service;

import co.sena.edu.themis.Entity.Coordination;
import co.sena.edu.themis.Repository.CoordinationRepository;
import co.sena.edu.themis.Service.Dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoordinationService implements Idao<Coordination, Long> {

    @Autowired
    private CoordinationRepository coordinationRepository;

    @Override
    public List<Coordination> findAll() {
        return coordinationRepository.findAll();
    }

    @Override
    public Coordination getById(Long id) {
        return coordinationRepository.getById(id);
    }

    @Override
    public void save(Coordination coordination) {
        coordinationRepository.save(coordination);
    }

    @Override
    public void deleteById(Long id) {
        coordinationRepository.deleteById(id);
    }

    @Override
    public Page<Coordination> findAll(Pageable pageable) {
        return this.coordinationRepository.findAll(pageable);
    }
}
