package med.voll.api.appointments;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record AppointmentDisplayDto(
      Long id,
      Long idPatient,
      Long idPhysician,
      @JsonFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime dateTime
) {
}
