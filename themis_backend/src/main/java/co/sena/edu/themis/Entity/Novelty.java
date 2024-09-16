package co.sena.edu.themis.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="novelties")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Novelty implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "noveltyDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date noveltyDate;

    @Column(name = "observation", nullable = false)
    private String observation;

    @Column(name = "status", nullable = false, length = 55)
    private String status;

    @Column(name = "noveltyFiles", nullable = false, length = 255)
    private String noveltyFiles;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @ToString.Exclude
    @JoinColumn(name = "fk_id_novelty_type", referencedColumnName = "id")
    private NoveltyType fk_id_novelty_type;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_person", referencedColumnName = "id")
    private Person fk_id_person;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_coordination", referencedColumnName = "id")
    private Coordination fk_id_coordination;

    @JsonBackReference
    @OneToMany(mappedBy = "fk_id_novelty", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> noveltyList;
}