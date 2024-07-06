package med.voll.api.appointments.validations;

import jakarta.xml.bind.ValidationException;
import med.voll.api.appointments.AppointmentDto;
import med.voll.api.physicians.PhysicianRepository;

public class AvailablePhysician {
   private PhysicianRepository physicianRepository;

   public AvailablePhysician(PhysicianRepository physicianRepository) {
      this.physicianRepository = physicianRepository;
   }

   public void validate(AppointmentDto data) throws ValidationException {
      if (data.idPhysician() == null) {
         return;
      }
      var availablePhysician = physicianRepository.findActiveById(data.idPhysician());
      if (!availablePhysician) {
         throw new ValidationException("Bookings not allowed with inactive physicians");
      }
   }
}
