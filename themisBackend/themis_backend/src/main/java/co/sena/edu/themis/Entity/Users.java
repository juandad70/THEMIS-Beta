package co.sena.edu.themis.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document", nullable = false)
    private Long document;

    @Column(name = "document_type", nullable = false)
    private String document_type;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_roles", referencedColumnName = "id")
    private Roles fk_id_roles;

    @OneToMany(mappedBy = "fk_id_user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<People> peopleSet;
}
