package co.sena.edu.themis.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "document")
    private Long document;

    @Column(name = "password")
    private String password;

    @Column(name = "type_document")
    private String type_document;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "fk_id_user", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "fk_id_role", referencedColumnName = "id")
    )
    private List<Role> roleList;

}
