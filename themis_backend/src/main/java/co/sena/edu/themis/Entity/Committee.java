package co.sena.edu.themis.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
    private Date committeeDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_proces", referencedColumnName = "id")
    @ToString.Exclude
    private Proces proces;
}
