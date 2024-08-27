package co.sena.edu.themis.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinationDto {
    private Long id;
    private String name;
    private CommitteeDto fk_id_committee;
}
