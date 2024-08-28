package co.sena.edu.themis.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

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

    @Column(name = "noveltyname", nullable = false) //esto es para el nombre de la novedad en agregar novedad
    private String nolveltyame;

    @Column(name = "noveltytype", nullable = false) //esto es para el formulario de registro de novedad
    private String nolveltytype;

    @Column(name = "nameApprentice", nullable = false) //esto es para el formulario de registro de novedad
    private String nameApprentice;

    @Column(name = "document", nullable = false) // esto para el formulario de registro de novedad
    private Long document;

    @Column(name = "program", nullable = false) // esto para el formulario de registro de novedad
    private String program;

    @Column(name = "apprenticeSheet", nullable = false) // esto para el formulario de registro de novedad
    private String apprenticeSheet;

    @Column(name = "novlety_description", nullable = false) // esto para el formulario  de novedad
    private String novelty_description;

    @Column(name ="fudaments",nullable = false )// esto para el formulario de registro de novedad
    private String fundaments;

    @Column(name = "status", nullable = false) // esto para  estados de novedad novedad
    private String status;

    @Column(name = "tramit_codition",nullable = false) // esto para el formulario de  novedad
    private  String tramit_codition;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_people", referencedColumnName = "id")
    private People fk_id_people;
}
