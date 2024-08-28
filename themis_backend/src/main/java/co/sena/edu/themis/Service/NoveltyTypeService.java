package co.sena.edu.themis.Service;



import co.sena.edu.themis.Entity.NoveltyType;
import co.sena.edu.themis.Repository.NoveltyTypeRepository;

import co.sena.edu.themis.Service.Dao.INoveltyTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoveltyTypeService implements INoveltyTypeDao {

    @Autowired
    private NoveltyTypeRepository noveltyTypeRepository;

    @Override
    public List<NoveltyType> findAll() {
        return noveltyTypeRepository.findAll();
    }

    @Override
    public Optional<NoveltyType> findById(Long id) {
        return noveltyTypeRepository.findById(id);
    }

    @Override
    public NoveltyType save(NoveltyType noveltyType) {
        return noveltyTypeRepository.save(noveltyType);
    }

    @Override
    public void deleteById(Long id) {
        noveltyTypeRepository.deleteById(id);
    }
}