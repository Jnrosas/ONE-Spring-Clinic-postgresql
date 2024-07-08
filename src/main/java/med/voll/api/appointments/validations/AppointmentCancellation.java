package med.voll.api.appointments.validations;


import jakarta.xml.bind.ValidationException;
import med.voll.api.appointments.AppointmentCancelDto;
import med.voll.api.appointments.AppointmentsRepository;

import java.time.Duration;
import java.time.LocalDateTime;

public class AppointmentCancellation implements AppointmentCancelValidator {
   private AppointmentsRepository appointmentsRepository;

   public AppointmentCancellation(AppointmentsRepository appointmentsRepository) {
      this.appointmentsRepository = appointmentsRepository;
   }

   @Override
   public void validate(AppointmentCancelDto data) throws ValidationException {
      var appointment = appointmentsRepository.getReferenceById(data.id());
      var now = LocalDateTime.now();
      var difference24hs = Duration.between(now, appointment.getDate()).toHours() < 24;
      if (difference24hs) {
         throw new ValidationException("Cancellations within 24 hs are not allowed");
      }

   }
}
