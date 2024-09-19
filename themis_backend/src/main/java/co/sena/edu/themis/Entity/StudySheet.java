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
@Table(name="study_sheets")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudySheet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "numberSheet")
    private Long numberSheet;
    @Column(name = "startDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "endDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "numberStudents", nullable = false)
    private int numberStudents;


    @OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_person", referencedColumnName = "id")
    @ToString.Exclude
    private Person fk_id_person;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_id_program", referencedColumnName = "id")
    @ToString.Exclude
    private Program fk_id_program;

    @OneToMany(mappedBy = "fk_id_study_sheet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ApplicationLetter> applicationLetterList;

}