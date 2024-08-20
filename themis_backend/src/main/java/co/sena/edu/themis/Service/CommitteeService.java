package co.sena.edu.themis.Service;

import co.sena.edu.themis.Entity.Committee;
import co.sena.edu.themis.Repository.CommitteeRepository;
import co.sena.edu.themis.Service.Dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommitteeService implements Idao<Committee, Long> {

    @Autowired
    private CommitteeRepository committeeRepository;

    @Override
    public List<Committee> findAll() {
        return committeeRepository.findAll();
    }

    @Override
    public Committee getById(Long id) {
        return committeeRepository.getById(id);
    }

    @Override
    public void save(Committee committee) {
        committeeRepository.save(committee);
    }

    @Override
    public void deleteById(Long id) {
        committeeRepository.deleteById(id);
    }

    @Override
    public Page<Committee> findAll(Pageable pageable) {
        return this.committeeRepository.findAll(pageable);
    }
}
