package co.sena.edu.themis.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="notifications")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notification implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    @Column(name="notiMessage", nullable = false)
    private String notiMessage;
    @Column(name="notiStatus", nullable = false, length = 55)
    private String notiStatus;
    @Column(name="dateAttention", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dateAttention;
    @Column(name="registrationDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date registrationDate;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_novelty", referencedColumnName = "id")
    private Novelty fk_id_novelty;
}
