package co.sena.edu.themis.Service;

import co.sena.edu.themis.Entity.Apprentice;
import co.sena.edu.themis.Repository.ApprenticeRepository;
import co.sena.edu.themis.Service.Dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprenticeService implements Idao<Apprentice, Long> {

    @Autowired
    private ApprenticeRepository apprenticeRepository;

    @Override
    public List<Apprentice> findAll() {
        return apprenticeRepository.findAll();
    }

    @Override
    public Apprentice getById(Long id) {
        return apprenticeRepository.getById(id);
    }

    @Override
    public void save(Apprentice apprentice) {
        apprenticeRepository.save(apprentice);
    }

    @Override
    public void deleteById(Long id) {
        apprenticeRepository.deleteById(id);
    }

    @Override
    public Page<Apprentice> findAll(Pageable pageable) {
        return this.apprenticeRepository.findAll(pageable);
    }
}
