package co.sena.edu.themis.Dto;

import lombok.Data;

@Data
public class ProgramDto {
    private Long id;
    private String programName; // Usar camelCase
    private String description;
    private String status;
    private Long fk_id_coordination;
}
