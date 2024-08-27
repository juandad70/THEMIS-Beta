package co.sena.edu.themis.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoveltyDto {
    private Long id;
    private Date nov_date;
    private String observation;
    private String status;
    private NoveltyTypeDto fk_id_novelty_type;
    private StudentDto fk_id_student;
    private CoordinationDto fk_id_coordination;
    private NotificationDto fk_id_notification;
}
