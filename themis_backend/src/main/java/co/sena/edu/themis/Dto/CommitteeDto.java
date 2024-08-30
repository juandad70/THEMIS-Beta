package co.sena.edu.themis.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitteeDto {
    private Long id;
    private Date committee_date;
    private ProceedingDto fk_id_proceeding;
    private PersonDto fk_id_person;
}
