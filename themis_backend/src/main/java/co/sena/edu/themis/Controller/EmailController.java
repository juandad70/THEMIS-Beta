package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Request.EmailRequest;
import co.sena.edu.themis.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-notification")
    public String sendNotification(@RequestBody EmailRequest emailRequest) {
        try {
            // Obtener la fecha actual en formato local (DD/MM/YYYY)
            String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // Reemplazar {date} en htmlContent con la fecha actual
            String htmlContent = emailRequest.getHtmlContent().replace("{date}", currentDate);

            // Enviar el correo electrónico con el contenido HTML actualizado
            emailService.sendHtmlEmail(emailRequest.getEmail(), emailRequest.getSubject(), htmlContent);

            return "Correo enviado con éxito";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al enviar el correo";
        }
    }
}
