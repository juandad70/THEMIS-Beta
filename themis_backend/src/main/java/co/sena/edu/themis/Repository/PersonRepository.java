package co.sena.edu.themis.Repository;

import co.sena.edu.themis.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
