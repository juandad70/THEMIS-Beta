package co.sena.edu.themis.Repository;

import co.sena.edu.themis.Entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
