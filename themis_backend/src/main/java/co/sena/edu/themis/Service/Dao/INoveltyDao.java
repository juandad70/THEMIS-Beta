package co.sena.edu.themis.Service.Dao;



import co.sena.edu.themis.Entity.Novelty;

import java.util.List;
import java.util.Optional;

public interface INoveltyDao {
    List<Novelty> findAll();
    Optional<Novelty> findById(Long id);
    Novelty save(Novelty novelty);
    void deleteById(Long id);

}
