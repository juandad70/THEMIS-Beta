package co.sena.edu.themis.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="study_sheets")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudySheet implements Serializable {
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_student", referencedColumnName = "id")
    private Student student;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_id_program", referencedColumnName = "id")
    private Program program;

}