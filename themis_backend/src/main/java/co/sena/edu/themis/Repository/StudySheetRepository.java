package co.sena.edu.themis.Repository;

import co.sena.edu.themis.Entity.StudySheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudySheetRepository extends JpaRepository<StudySheet, Long> {
}
