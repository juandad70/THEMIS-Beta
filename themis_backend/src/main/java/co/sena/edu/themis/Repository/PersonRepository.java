package co.sena.edu.themis.Repository;

import co.sena.edu.themis.Entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT pe FROM Person pe WHERE pe.email = :email")
    Person findByEmail(@Param("email") String email);
}
