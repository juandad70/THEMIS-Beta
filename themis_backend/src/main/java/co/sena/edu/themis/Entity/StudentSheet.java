package co.sena.edu.themis.Entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="StudentSheet")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentSheet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date start_date;
    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date end_date;
    @Column(name = "number_students", nullable = false)
    private int number_students;


}
