package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-notification")
    public String sendNotification(@RequestParam String email) {
        try {
            String subject = "Notificación de novedad";
            String text = "Este es un mensaje de notificación de la novedad .";
            emailService.sendSimpleEmail(email, subject, text);
            return "Correo enviado con éxito";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al enviar el correo";
        }
    }
}
