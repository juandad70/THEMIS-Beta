package co.sena.edu.themis.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {
    private Long id;
    private String event_file;
    private ProceedingDto fk_id_proceeding;
    private PersonDto fk_id_person;
}
