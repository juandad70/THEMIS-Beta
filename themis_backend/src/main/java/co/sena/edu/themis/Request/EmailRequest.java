package co.sena.edu.themis.Request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailRequest {
    private String email;
    private String subject;
    private String htmlContent;
}
