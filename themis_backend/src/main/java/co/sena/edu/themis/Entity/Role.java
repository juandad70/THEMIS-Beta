package co.sena.edu.themis.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "roleList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<User> userList;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "roles_novelty_types",
            joinColumns = @JoinColumn(name = "fk_role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "fk_novelty_type_id", referencedColumnName = "id")
    )
    private List<NoveltyType> noveltyTypeList;
}
