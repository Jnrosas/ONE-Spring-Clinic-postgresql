package med.voll.api.appointments.validations;

import jakarta.xml.bind.ValidationException;
import med.voll.api.appointments.AppointmentCancelDto;

public interface AppointmentCancelValidator {
   void validate(AppointmentCancelDto cancelDto) throws ValidationException;
}
