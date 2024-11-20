package co.sena.edu.themis.Repository;

import co.sena.edu.themis.Entity.Apprentice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprenticeRepository extends JpaRepository<Apprentice, Long> {
}
