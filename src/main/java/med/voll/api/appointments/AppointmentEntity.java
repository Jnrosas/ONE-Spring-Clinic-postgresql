package med.voll.api.appointments;

import jakarta.persistence.*;
import lombok.*;
import med.voll.api.patients.PatientEntity;
import med.voll.api.patients.PatientRegisterDto;
import med.voll.api.physicians.PhysicianEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class AppointmentEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "id_patient")
   private PatientEntity patient;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "id_physician")
   private PhysicianEntity physician;

   private LocalDateTime date;
   private Boolean active = true;

   @Enumerated(EnumType.STRING)
   @Column(name = "cancel_reason")
   private CancelReason cancelReason;


   public void deactivateAppointment() {
      this.active = false;
   }
}
