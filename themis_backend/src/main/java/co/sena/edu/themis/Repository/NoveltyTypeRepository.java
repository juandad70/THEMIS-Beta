package co.sena.edu.themis.Repository;



import co.sena.edu.themis.Entity.NoveltyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoveltyTypeRepository extends JpaRepository<NoveltyType, Long> {
    @Query("SELECT nt FROM NoveltyType nt JOIN FETCH nt.roles")
    List<NoveltyType> findAllWithRoles();
}