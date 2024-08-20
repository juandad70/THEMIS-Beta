package co.sena.edu.themis.Entity;


import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
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

}