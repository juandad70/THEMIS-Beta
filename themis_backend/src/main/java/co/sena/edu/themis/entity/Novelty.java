package co.sena.edu.themis.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_novelty_type", referencedColumnName = "id")
    private NoveltyType noveltyType;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_student", referencedColumnName = "id")
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_coordination", referencedColumnName = "id")
    private Coordination coordination;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_notification", referencedColumnName = "id")
    private Notification notification;
}
