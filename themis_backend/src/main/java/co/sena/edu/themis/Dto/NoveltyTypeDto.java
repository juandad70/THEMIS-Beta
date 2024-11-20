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
    private String nameNovelty;
    private boolean noveltyState;
    private String description;
    private String procedureDescription;
}
