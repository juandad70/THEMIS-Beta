package co.sena.edu.themis.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="people")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    @Column(name="name", nullable = false, length = 45)
    private String name;
    @Column(name="lastname", nullable = false, length = 45)
    private String lastname;
    @Column(name="email", nullable = false, length = 50)
    private String email;
    @Column(name="phone", nullable = false, length = 10)
    private String phone;
    @Column(name="status", nullable = false, length = 55)
    private String status;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_user", referencedColumnName = "id")
    private User fk_id_user;

    @OneToMany(mappedBy = "fk_id_person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ApplicationLetter> applicationLetterList;

    @OneToMany(mappedBy = "fk_id_person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Committee> committeeList;

    @OneToMany(mappedBy = "fk_id_person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Novelty> noveltyList;
}
