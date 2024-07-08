package med.voll.api.appointments;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentCancelDto(
      @NotNull Long id,
      Long idPatient,
      Long IdPhysician,
      @NotNull @JsonFormat(pattern = "dd-MM-yyyy HH:mm")LocalDateTime date,
      @NotNull CancelReason cancelReason
      ) {
}
