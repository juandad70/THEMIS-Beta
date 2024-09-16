package co.sena.edu.themis.Repository;

import co.sena.edu.themis.Entity.Coordination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinationRepository extends JpaRepository<Coordination, Long> {
}
