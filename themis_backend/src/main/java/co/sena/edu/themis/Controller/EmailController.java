package co.sena.edu.themis.Controller;

import co.sena.edu.themis.Business.EmailBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailBusiness emailBusiness;

    // Endpoint para enviar un correo electrónico simple
    @PostMapping("/send")
    public ResponseEntity<String> sendSimpleEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text) {
        try {
            emailBusiness.sendSimpleEmail(to, subject, text);
            return ResponseEntity.ok("Correo electrónico enviado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el correo: " + e.getMessage());
        }
    }


    // Endpoint para enviar el código de autenticación 2FA
    @PostMapping("/send-2fa")
    public ResponseEntity<String> send2FACode(@RequestParam String to, @RequestParam String code) {
        try {
            emailBusiness.send2FACode(to, code);
            return ResponseEntity.ok("Código 2FA enviado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el código 2FA: " + e.getMessage());
        }
    }
}
