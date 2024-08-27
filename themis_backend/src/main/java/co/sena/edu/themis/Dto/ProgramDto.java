package co.sena.edu.themis.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgramDto {
    private Long id;
    private String program_name;
    private String description;
    private String status;
    private CoordinationDto fk_id_coordination;
}
