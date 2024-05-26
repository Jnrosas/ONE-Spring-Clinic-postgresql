package med.voll.api.patient;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.address.Address;

@Entity
@Table(name = "patients")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PatientEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;
   private String email;
   private String dni;
   private String phone_number;
   private Boolean active;

   @Embedded
   private Address address;

   public PatientEntity(PatientRegisterDto patientRegisterDTO) {
      this.name = patientRegisterDTO.name();
      this.email = patientRegisterDTO.email();
      this.dni = patientRegisterDTO.dni();
      this.phone_number = patientRegisterDTO.phone_number();
      this.active = true;
      this.address = new Address(patientRegisterDTO.address());
   }

   public void updateData(PatientUpdateDto update) {
      if (update.name() != null) {
         this.name = update.name();
      }
      if (update.dni() != null) {
         this.dni = update.dni();
      }
      if (update.address() != null) {
         this.address = address.updateAddress(update.address());
      }
   }

   public void deactivatePatient() {
      this.active = false;
   }
}
