package med.voll.api.appointments.validations;

import jakarta.xml.bind.ValidationException;
import med.voll.api.appointments.AppointmentDto;
import med.voll.api.appointments.AppointmentsRepository;

public class OneAppointmentADay {
   private AppointmentsRepository appointmentsRepository;

   public OneAppointmentADay(AppointmentsRepository appointmentsRepository) {
      this.appointmentsRepository = appointmentsRepository;
   }

   public void valida(AppointmentDto data) throws ValidationException {
      var start = data.date().withHour(7);
      var end = data.date().withHour(18);
      var patientWithAppointment = appointmentsRepository
            .existsByPatientIdAndDateBetween(data.idPatient(), start, end);
      if (patientWithAppointment) {
         throw new ValidationException("That patient already has an appointment that day");
      }
   }
}
