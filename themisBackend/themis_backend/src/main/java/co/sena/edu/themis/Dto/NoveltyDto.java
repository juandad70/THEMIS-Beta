package co.sena.edu.themis.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NoveltyDto {
    private int id;
    private String nameApprentice;
    private Long document;
    private String program;
    private String apprenticeSheet;
    private String description;
    private File files;
    private String status;

}
