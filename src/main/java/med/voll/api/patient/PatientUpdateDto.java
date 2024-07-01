package med.voll.api.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.address.AddressDto;

public record PatientUpdateDto(
      @NotNull Long id,
      String name,
      String dni,
      @Valid AddressDto address,
      String password
) {
}
