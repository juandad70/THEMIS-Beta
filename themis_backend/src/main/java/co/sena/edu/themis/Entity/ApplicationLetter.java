package co.sena.edu.themis.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="applications_letters")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ApplicationLetter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "applica_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date applica_date;

    @Column(name = "fundament", nullable = false)
    private String fundament;

    @Column(name = "signature", nullable = false, length = 50)
    private String signature;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_people", referencedColumnName = "id")
    private Person fk_id_people;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_study_sheet", referencedColumnName = "id")
    private StudySheet fk_id_study_sheet;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_program", referencedColumnName = "id")
    private Program fk_id_program;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_nov_type", referencedColumnName = "id")
    private NoveltyType noveltyType; // Renombrado de 'fk_id_nov_type' a 'noveltyType'
}