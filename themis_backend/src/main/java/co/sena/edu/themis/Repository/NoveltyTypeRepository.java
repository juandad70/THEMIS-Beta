package co.sena.edu.themis.Repository;



import co.sena.edu.themis.Entity.NoveltyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoveltyTypeRepository extends JpaRepository<NoveltyType, Long> {
}