package co.sena.edu.themis.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoveltyTypeDto {
    private Long id;
    private Date novel_date;
    private String novel_type;
    private String novel_state;
    private String sofia_certainty;
    private String description;
}
