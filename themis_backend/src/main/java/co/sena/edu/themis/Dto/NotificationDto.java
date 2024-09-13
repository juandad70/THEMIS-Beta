package co.sena.edu.themis.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    private Long id;
    private String notifi_message;
    private String notifi_status;
    private Date date_attention;
    private Date registration_date;
    private NoveltyDto fk_id_novelty;
}
