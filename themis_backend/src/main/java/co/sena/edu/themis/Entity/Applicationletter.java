package co.sena.edu.themis.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="Applicationletter ")
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

}
