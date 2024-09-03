package co.sena.edu.themis.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder


public class FileDto {
    private UUID id;
    private String name;
    private String type;
    private byte[] data;
}
