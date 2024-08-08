package co.sena.edu.themis.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "People")
@NoArgsConstructor
@Data
public class People implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private Long phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_user", referencedColumnName = "document")
    private Users fk_id_user;

    @OneToMany(mappedBy = "fk_id_people", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Novelty> noveltySet = new HashSet<>();

    public People(Long id, String name, String lastName, String email, Long phone) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
}