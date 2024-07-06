package med.voll.api.appointments.validations;

import jakarta.xml.bind.ValidationException;
import med.voll.api.appointments.AppointmentDto;
import med.voll.api.patients.PatientRepository;

public class AvailablePatient {
   private PatientRepository patientRepository;

   public AvailablePatient(PatientRepository patientRepository) {
      this.patientRepository = patientRepository;
   }

   public void validate(AppointmentDto data) throws ValidationException {
      if (data.idPatient() == null) {
         return;
      }
      var availablePatient = patientRepository.findActiveById(data.idPatient());
      if (!availablePatient) {
         throw new ValidationException("Bookings not allowed with inactive patients");
      }
   }
}
