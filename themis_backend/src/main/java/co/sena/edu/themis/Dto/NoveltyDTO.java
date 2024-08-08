package co.sena.edu.themis.Dto;

import lombok.Data;

@Data
public class NoveltyDTO {
    private Long id;
    private String noveltyName;
    private String noveltyType;
    private String nameApprentice;
    private Long document;
    private String program;
    private String apprenticeSheet;
    private String noveltyDescription;
    private String fundaments;
    private String files;  // Cambiado a String para almacenar la ruta del archivo
    private String status;
    private String tramitCondition;
}