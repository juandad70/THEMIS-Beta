package co.sena.edu.themis.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationLetterDto {
    private Long id;
    private Date applicationDate;
    private String fundament;
    private String signature;
    private PersonDto fk_id_person;
    private StudySheetDto fk_id_study_sheet;
    private ProgramDto fk_id_program;
    private NoveltyTypeDto fk_id_nov_type;
}
