package co.sena.edu.themis.Business;

import co.sena.edu.themis.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailBusiness {

    @Autowired
    private EmailService emailService;

    // Método para manejar la lógica de negocio relacionada con el envío de notificaciones de novedad
    public String handleAttendanceNotification(String email) {
        try {
            String subject = "Notificación de Inasistencia";
            String text = "Este es un mensaje de notificación de inasistencia.";
            emailService.sendSimpleEmail(email, subject, text);
            return "Correo enviado con éxito";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al enviar el correo";
        }
    }

    // Método para manejar la lógica de negocio relacionada con el envío de códigos de autenticación
    public String handleSend2FACode(String email, String code) {
        try {
            emailService.sendEmail(email, code);
            return "Código de autenticación enviado con éxito";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al enviar el código de autenticación";
        }
    }
}
