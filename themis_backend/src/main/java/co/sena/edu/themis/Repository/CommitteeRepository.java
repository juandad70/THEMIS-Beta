package co.sena.edu.themis.Repository;

import co.sena.edu.themis.Entity.Committee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommitteeRepository extends JpaRepository<Committee, Long> {
}
