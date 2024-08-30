package co.sena.edu.themis.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="committees")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Committee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "committee_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date committee_date;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_proceeding", referencedColumnName = "id")
    private Proceeding fk_id_proceeding;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_person", referencedColumnName = "id")
    private Person fk_id_person;

    @OneToMany(mappedBy = "fk_id_committee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Coordination> coordinationList;

}
