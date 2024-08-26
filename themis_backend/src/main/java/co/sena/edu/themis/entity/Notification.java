package co.sena.edu.themis.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

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
    @Column(name="notifi_message", nullable = false)
    private String notifi_message;
    @Column(name="notifi_status", nullable = false, length = 55)
    private String notifi_status;
    @Column(name="date_attention", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date_attention;
    @Column(name="registration_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date registration_date;
}
