package co.sena.edu.themis.Repository;

import co.sena.edu.themis.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roleList WHERE u.document = :document")
    User findByIdWithRoles(@Param("document") Long document);

}
