package co.sena.edu.themis.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name="people")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;
    @Column(name="name", nullable = false, length = 45)
    private String name;
    @Column(name="lastname", nullable = false, length = 45)
    private String lastname;
    @Column(name="email", nullable = false, length = 50)
    private String email;
    @Column(name="phone", nullable = false, length = 10)
    private String phone;
    @Column(name="status", nullable = false, length = 55)
    private String status;

}
