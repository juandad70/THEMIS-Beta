package co.sena.edu.themis.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Table(name="journeys")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Journey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name" )
    private String name;

    @OneToMany(mappedBy = "fk_id_journey", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StudySheet> studySheetList;
}
