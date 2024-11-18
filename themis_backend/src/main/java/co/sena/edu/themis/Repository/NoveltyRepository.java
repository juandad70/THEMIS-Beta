package co.sena.edu.themis.Repository;



import co.sena.edu.themis.Entity.Novelty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoveltyRepository extends JpaRepository<Novelty, Long> {

}
