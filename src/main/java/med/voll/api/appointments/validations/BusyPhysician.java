package med.voll.api.appointments.validations;

import jakarta.xml.bind.ValidationException;
import med.voll.api.appointments.AppointmentDto;
import med.voll.api.appointments.AppointmentsRepository;
import org.springframework.stereotype.Component;

@Component
public class BusyPhysician implements AppointmentValidator {
   private AppointmentsRepository appointmentsRepository;

   public BusyPhysician(AppointmentsRepository appointmentsRepository) {
      this.appointmentsRepository = appointmentsRepository;
   }

   public void validate(AppointmentDto data) throws ValidationException {

      if (data.idPhysician() == null) {
         return;
      }
      var busyPhysician = appointmentsRepository
            .existsByPhysicianIdAndDate(data.idPhysician(), data.date());

      if (busyPhysician) {
         throw new ValidationException("That physician has another appointment at that time and day");
      }
   }

}
