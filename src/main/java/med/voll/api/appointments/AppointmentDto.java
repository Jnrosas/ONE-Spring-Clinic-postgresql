package med.voll.api.appointments;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentDto(
      Long id,
      @NotNull Long idPatient,
      Long idPhysician,
      @NotNull @Future @JsonFormat(pattern = "dd-MM-yyyy HH:mm") LocalDateTime date
) {
}
