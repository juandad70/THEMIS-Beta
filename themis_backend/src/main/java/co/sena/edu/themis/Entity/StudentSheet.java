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
@Table(name = "StudentSheet")
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

    @Column(name = "study_sessions", nullable = false)
    private int study_sessions;

    @OneToMany(mappedBy = "fk_id_studentsheet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<People> peopleList;
}
