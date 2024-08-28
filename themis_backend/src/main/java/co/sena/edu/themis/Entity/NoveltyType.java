package co.sena.edu.themis.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
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

    @Column(name = "novel_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date novel_date;

    @Column(name = "novel_type", nullable = false, length = 55)
    private String novel_type;

    @Column(name = "novel_state", nullable = false, length = 55)
    private String novel_state;

    @Column(name= "sofia_certainty", nullable = false)
    private String sofia_certainty;

    @Column(name="description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "noveltyType", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<ApplicationLetter> applicationLetterSet;
}
