package co.sena.edu.themis.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Table(name = "apprentices") // Cambié el nombre de la tabla para que esté en plural
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Apprentice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // Relación con la persona
    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_person", referencedColumnName = "id")
    @ToString.Exclude
    private Person fk_id_person; // Relación con la entidad Persona (persona_id)

    // Relación con el 'StudySheet' - un aprendiz pertenece a una ficha
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_study_sheet", referencedColumnName = "id")
    @ToString.Exclude
    private StudySheet fk_id_study_sheet;

    @OneToMany(mappedBy = "fk_id_apprentice", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Novelty> noveltyList;
}
