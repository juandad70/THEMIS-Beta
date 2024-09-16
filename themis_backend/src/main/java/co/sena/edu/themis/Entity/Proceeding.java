package co.sena.edu.themis.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

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
    @Column(name = "proceedingFile", nullable = false)
    private String proceedingFile;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_nov_type", referencedColumnName = "id")
    private NoveltyType fk_id_nov_type;

    @OneToMany(mappedBy = "fk_id_proceeding", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Committee> committeeList;
}
