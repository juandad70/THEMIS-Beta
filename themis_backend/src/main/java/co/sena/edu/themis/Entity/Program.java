package co.sena.edu.themis.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="programs")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Program implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "program_name", nullable = false, length = 45)
    private String program_name;
    @Column(name = "description", nullable = false)
    private String description;
    @Column(name = "status", nullable = false, length = 55)
    private String status;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_coordination", referencedColumnName = "id")
    private Coordination fk_id_coordination;

    @OneToMany(mappedBy = "fk_id_program", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StudySheet> studySheetList;

    @OneToMany(mappedBy = "fk_id_program", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ApplicationLetter> applicationLetterList;
}