package co.sena.edu.themis.Service;


import co.sena.edu.themis.Entity.Novelty;
import co.sena.edu.themis.Repository.NoveltyRepository;
import co.sena.edu.themis.Service.Dao.Idao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoveltyService implements Idao<Novelty, Long> {

    @Autowired
    private NoveltyRepository noveltyRepository;


    @Override
    public List<Novelty> findAll() {
        return noveltyRepository.findAll();
    }

    @Override
    public Novelty getById(Long id) {
        return noveltyRepository.getById(id);
    }

    @Override
    public void save(Novelty novelty) {
        noveltyRepository.save(novelty);
    }

    @Override
    public void delete(Long id) {
        noveltyRepository.deleteById(id);
    }

    @Override
    public Page<Novelty> findAllPage(PageRequest pageable) {
        return this.noveltyRepository.findAll(pageable);
    }
}