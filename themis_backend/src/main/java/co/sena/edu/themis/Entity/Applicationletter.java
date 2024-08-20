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
@Table(name="Applicationletter")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Applicationletter  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "applica_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date applica_date;

    @Column(name = "fundament", nullable = false)
    private String fundament;

    @Column(name = "signature", nullable = false, length = 50)
    private String signature;

    @OneToMany(mappedBy = "applicationletter", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Novelty> noveltyList;
}
