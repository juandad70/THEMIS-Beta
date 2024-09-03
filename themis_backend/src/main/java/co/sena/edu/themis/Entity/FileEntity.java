package co.sena.edu.themis.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "files")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String type;
    @Lob
    private byte[] data;
}
