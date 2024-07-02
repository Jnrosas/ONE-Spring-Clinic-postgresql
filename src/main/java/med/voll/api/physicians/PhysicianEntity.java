package med.voll.api.physicians;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.address.Address;

@Entity
@Table(name = "physicians")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class PhysicianEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;
   private String email;
   private String dni;
   private String phone_number;
   private Boolean active;

   @Enumerated(EnumType.STRING)
   private SpecialtyEnum specialty;

   @Embedded
   private Address address;

   public PhysicianEntity(PhysicianRegisterDto physicianRegisterDTO) {
      this.name = physicianRegisterDTO.name();
      this.email = physicianRegisterDTO.email();
      this.dni = physicianRegisterDTO.dni();
      this.phone_number = physicianRegisterDTO.phone_number();
      this.active = true;
      this.specialty = physicianRegisterDTO.specialty();
      this.address = new Address(physicianRegisterDTO.address());
   }

   public void updateData(PhysicianUpdateDto update) {
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

   public void deactivatePhyisician() {
      this.active = false;
   }
}
