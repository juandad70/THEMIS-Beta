package co.sena.edu.themis.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name="proceedings")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class  Proceeding implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "name", nullable = false, length = 45)
    private String name;
    @Column(name = "proceeding_file", nullable = false)
    private String proceeding_file;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_nov_type", referencedColumnName = "id")
    private NoveltyType noveltyType;
}
