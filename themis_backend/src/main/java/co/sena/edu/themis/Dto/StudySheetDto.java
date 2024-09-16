package co.sena.edu.themis.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudySheetDto {
    private Long id;
    private Date startDate;
    private Date endDate;
    private int numberStudents;
    private PersonDto fk_id_person;
    private ProgramDto fk_id_program;
}
