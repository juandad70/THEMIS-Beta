package co.sena.edu.themis.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="novelties_types")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class NoveltyType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nameNovelty", nullable = false, length = 100)
    private String nameNovelty;

    @Column(name = "noveltyState", nullable = false)
    private boolean noveltyState;

    @Column(name="description", nullable = false)
    private String description;

    @Column(name = "procedureDescription", nullable = false, length = 120)
    private String procedureDescription;

    @JsonBackReference
    @OneToMany(mappedBy = "fk_id_novelty_type", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Novelty> noveltyList;

    @OneToMany(mappedBy = "fk_id_nov_type", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ApplicationLetter> applicationLetterList;

    @ManyToMany(mappedBy = "noveltyTypeList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Role> roleList;
}