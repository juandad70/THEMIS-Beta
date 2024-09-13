package co.sena.edu.themis.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Column(name = "nov_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date nov_date;

    @Column(name = "observation", nullable = false)
    private String observation;

    @Column(name = "status", nullable = false, length = 55)
    private String status;

    @Column(name = "image", nullable = false, length = 255)
    private String image;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_novelty_type", referencedColumnName = "id")
    private NoveltyType fk_id_novelty_type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_person", referencedColumnName = "id")
    private Person fk_id_person;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_coordination", referencedColumnName = "id")
    private Coordination fk_id_coordination;

    @OneToMany(mappedBy = "fk_id_novelty", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> noveltyList;
}