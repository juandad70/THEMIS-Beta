package co.sena.edu.themis.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Column(name = "nameNovelty", nullable = false, length = 55)
    private String nameNovelty;

    @Column(name = "noveltyState", nullable = false, length = 55)
    private String noveltyState;

    @Column(name= "sofiaCertainty")
    private String sofiaCertainty;

    @Column(name="description", nullable = false)
    private String description;

    @JsonBackReference
    @OneToMany(mappedBy = "fk_id_novelty_type", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Novelty> noveltyList;

    @OneToMany(mappedBy = "fk_id_nov_type", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ApplicationLetter> applicationLetterList;
}