package co.sena.edu.themis.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprenticeDto {
    private Long id;
    private PersonDto fk_id_person;
    private StudySheetDto fk_id_study_sheet;
}
