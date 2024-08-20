package co.sena.edu.themis.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="Proces")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Proces implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "proceeding_file", nullable = false)
    private String proceedingFile;

    @OneToMany(mappedBy = "fk_id_proces", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Novelty> noveltyList;

    @OneToMany(mappedBy = "proces", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Committee> committees;
}
