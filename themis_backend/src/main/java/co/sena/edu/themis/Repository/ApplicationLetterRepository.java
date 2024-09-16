package co.sena.edu.themis.Repository;

import co.sena.edu.themis.Entity.ApplicationLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationLetterRepository extends JpaRepository<ApplicationLetter, Long> {
}
