package co.sena.edu.themis.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "Novelty")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Novelty implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "noveltyname", nullable = false)
    private String noveltyName;

    @Column(name = "noveltytype", nullable = false)
    private String noveltyType;

    @Column(name = "nameApprentice", nullable = false)
    private String nameApprentice;

    @Column(name = "document", nullable = false)
    private Long document;

    @Column(name = "program", nullable = false)
    private String program;

    @Column(name = "apprenticeSheet", nullable = false)
    private String apprenticeSheet;

    @Column(name = "novelty_description", nullable = false)
    private String noveltyDescription;

    @Column(name = "fundaments", nullable = false)
    private String fundaments;

    @Column(name = "files", nullable = true)
    private String files;  // Cambiado a String para almacenar la ruta del archivo

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "tramit_condition", nullable = false)
    private String tramitCondition;



    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_people", referencedColumnName = "id")
    private People fk_id_people;
}