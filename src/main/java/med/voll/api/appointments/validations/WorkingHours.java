package med.voll.api.appointments.validations;

import jakarta.xml.bind.ValidationException;
import med.voll.api.appointments.AppointmentDto;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class WorkingHours implements AppointmentValidator {

   public void validate(AppointmentDto data) throws ValidationException {
      var sunday = DayOfWeek.SUNDAY.equals(data.date().getDayOfWeek());
      var beforeOpening = data.date().getHour() < 7;
      var afterClosing = data.date().getHour() > 18 ||
            (data.date().getHour() == 18 && data.date().getMinute() > 0);

      if (sunday || beforeOpening || afterClosing) {
         throw new ValidationException("Working hours are Mon to Sat from 7 to 19hs");
      }
   }
}
