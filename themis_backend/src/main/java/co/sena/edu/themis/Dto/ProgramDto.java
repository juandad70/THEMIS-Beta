package co.sena.edu.themis.Dto;

import lombok.Data;

@Data
public class ProgramDto {
    private Long id;
    private String programName;
    private String description;
    private String status;
    private CoordinationDto fk_id_coordination;
}
