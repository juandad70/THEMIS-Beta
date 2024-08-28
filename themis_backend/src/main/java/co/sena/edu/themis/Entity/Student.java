package co.sena.edu.themis.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "state", nullable = false, length = 55)
    private String state;

    @Column(name = "document_number", nullable = false, length = 10)
    private String document_number;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_people", referencedColumnName = "id")
    private Person fk_id_people;

    // Método getPerson corregido
    public Person getPerson() {
        return fk_id_people;
    }
}
