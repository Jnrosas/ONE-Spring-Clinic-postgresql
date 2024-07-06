package med.voll.api.appointments.validations;

import jakarta.xml.bind.ValidationException;
import med.voll.api.appointments.AppointmentDto;

import java.time.DayOfWeek;

public class WorkingHours {
   public void validate(AppointmentDto data) throws ValidationException {
      var sunday = DayOfWeek.SUNDAY.equals(data.date().getDayOfWeek());
      var beforeOpening = data.date().getHour() < 7;
      var afterClosing = data.date().getHour() > 19;

      if (sunday || beforeOpening || afterClosing) {
         throw new ValidationException("Working hours are Mon to Sat from 7 to 19hs");
      }
   }
}
