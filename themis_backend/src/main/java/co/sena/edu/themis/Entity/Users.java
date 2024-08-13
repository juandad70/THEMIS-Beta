package co.sena.edu.themis.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;


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
    @JoinColumn(name = "fk_id_role", referencedColumnName = "id")
    @ToString.Exclude
    private Role fk_id_role;

    @OneToMany(mappedBy = "fk_id_users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<People> peopleList;
}
