package med.voll.api.appointments.validations;

import jakarta.xml.bind.ValidationException;
import med.voll.api.appointments.AppointmentDto;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class TimeInAdvance implements AppointmentValidator {

   public void validate(AppointmentDto data) throws ValidationException {
      var now = LocalDateTime.now();
      var appointmentTime = data.date();
      var differenceOf30Min = Duration.between(now, appointmentTime).toMinutes() < 30;
      if (differenceOf30Min) {
         throw new ValidationException("Bookings with less than 30 min of time are not allowed");
      }
   }
}
