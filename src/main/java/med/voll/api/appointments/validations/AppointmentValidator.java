package med.voll.api.appointments.validations;

import jakarta.xml.bind.ValidationException;
import med.voll.api.appointments.AppointmentDto;

public interface AppointmentValidator {
   void validate(AppointmentDto data) throws ValidationException;
}
