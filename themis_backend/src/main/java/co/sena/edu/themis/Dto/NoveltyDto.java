package co.sena.edu.themis.Dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoveltyDto {
    private Long id;
    private Date noveltyDate;
    private String observation;
    private String status;
    private String noveltyFiles;
    private NoveltyTypeDto fk_id_novelty_type;
    private PersonDto fk_id_person;
    private CoordinationDto fk_id_coordination;
}