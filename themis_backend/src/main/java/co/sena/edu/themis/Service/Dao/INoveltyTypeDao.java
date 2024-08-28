package co.sena.edu.themis.Service.Dao;


import co.sena.edu.themis.Entity.NoveltyType;
import java.util.List;
import java.util.Optional;

public interface INoveltyTypeDao {
    List<NoveltyType> findAll();
    Optional<NoveltyType> findById(Long id);
    NoveltyType save(NoveltyType noveltyType);
    void deleteById(Long id);
}