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
    private Date start_date;
    private Date end_date;
    private int number_students;
    private StudentDto fk_id_student;
    private ProgramDto fk_id_program;
}
