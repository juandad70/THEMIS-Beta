package co.sena.edu.themis.Repository;

import co.sena.edu.themis.Entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
