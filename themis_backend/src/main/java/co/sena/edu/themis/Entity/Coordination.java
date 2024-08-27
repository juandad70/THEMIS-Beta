package co.sena.edu.themis.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name="coordinations")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Coordination implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    @Column(name="name", nullable = false, length = 45)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_committee", referencedColumnName = "id")
    private Committee fk_id_committee;
}
