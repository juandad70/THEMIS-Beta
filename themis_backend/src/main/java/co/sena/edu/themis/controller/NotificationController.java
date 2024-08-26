package co.sena.edu.themis.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NotificationController {

    @GetMapping("/obtainNotifi")
    public String obtainNotification() {
        return "Notificacion obtenida con exito";
    }

    @GetMapping("/obtainNotifi/{id}")
    public String obtainNotificationById() {
        return "Datos de Notificacion obtenidos con exito";
    }

    @PostMapping("/registerNotifi")
    public String registerNotification() {
        return "Notificacion registrada con exito";
    }

    @PostMapping("/createNotifi")
    public String createNotification() {
        return "Notificacion creada con exito";
    }

    @DeleteMapping("/deleteNotifi/{id}")
    public String deleteNotification(@PathVariable int id) {
        return "Notificacion eliminada con exito";
    }
}
