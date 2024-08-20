package co.sena.edu.themis.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "People")
@NoArgsConstructor
@AllArgsConstructor
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
    @JoinColumn(name = "fk_id_users", referencedColumnName = "document")
    @ToString.Exclude
    private Users fk_id_users;

    @OneToMany(mappedBy = "fk_id_people", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Novelty> noveltyList;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_program", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private Program fk_id_program;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_studentsheet", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private StudentSheet fk_id_studentsheet;
}
