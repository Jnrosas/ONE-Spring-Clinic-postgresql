package med.voll.api.physicians;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.voll.api.address.AddressDto;

public record PhysicianUpdateDto(
      @NotNull Long id,
      String name,
      String dni,
      @Valid AddressDto address
) {
}
