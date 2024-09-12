package co.sena.edu.themis.Business;

import co.sena.edu.themis.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailBusiness {

    private static final Logger logger = LoggerFactory.getLogger(EmailBusiness.class);

    @Autowired
    private EmailService emailService;

    // Método para enviar un correo electrónico simple
    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            emailService.sendSimpleEmail(to, subject, text);
            logger.info("Correo electrónico enviado a: {}", to);
        } catch (Exception e) {
            logger.error("Error enviando correo a: {}, Error: {}", to, e.getMessage());
            throw new RuntimeException("Error enviando el correo electrónico");
        }
    }

    // Método para enviar el correo con el código de 2FA
    public void send2FACode(String to, String code) {
        try {
            emailService.sendEmail(to, code);
            logger.info("Código 2FA enviado a: {}", to);
        } catch (Exception e) {
            logger.error("Error enviando código 2FA a: {}, Error: {}", to, e.getMessage());
            throw new RuntimeException("Error enviando el código de autenticación");
        }
    }
}
