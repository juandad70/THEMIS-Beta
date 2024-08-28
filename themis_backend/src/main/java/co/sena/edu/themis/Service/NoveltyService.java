package co.sena.edu.themis.Service;

import co.sena.edu.themis.Entity.Novelty;
import co.sena.edu.themis.Repository.NoveltyRepository;

import co.sena.edu.themis.Service.Dao.INoveltyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoveltyService implements INoveltyDao {

    @Autowired
    private NoveltyRepository noveltyRepository;

    @Override
    public List<Novelty> findAll() {
        return noveltyRepository.findAll();
    }

    @Override
    public Optional<Novelty> findById(Long id) {
        return noveltyRepository.findById(id);
    }


    @Override
    public Novelty save(Novelty novelty) {
        return noveltyRepository.save(novelty);
    }

    @Override
    public void deleteById(Long id) {
        noveltyRepository.deleteById(id);
    }
}