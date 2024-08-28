package co.sena.edu.themis.Dto;



import lombok.Data;
import java.util.Date;

@Data
public class NoveltyDto {
    private Long id;
    private Date nov_date;
    private String observation;
    private String status;
    private Long fk_id_novelty_type;
    private Long fk_id_student;
    private Long fk_id_coordination;
    private Long fk_id_notification;

}