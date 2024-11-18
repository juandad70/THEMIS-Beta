package co.sena.edu.themis.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProceedingDto {
    private Long id;
    private String name;
    private String proceedingFile;
    private NoveltyTypeDto fk_id_nov_type;
}
