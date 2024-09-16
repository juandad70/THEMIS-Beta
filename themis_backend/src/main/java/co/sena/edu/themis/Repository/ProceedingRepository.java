package co.sena.edu.themis.Repository;

import co.sena.edu.themis.Entity.Proceeding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProceedingRepository extends JpaRepository<Proceeding, Long> {
}
