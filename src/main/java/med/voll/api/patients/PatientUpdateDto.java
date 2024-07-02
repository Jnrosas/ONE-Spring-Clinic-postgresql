package med.voll.api.patients;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.address.AddressDto;

public record PatientUpdateDto(
      @NotNull Long id,
      String name,
      @Email String email,
      @Pattern(regexp = "\\d{7,8}") String dni,
      @Pattern(regexp = "\\d{9,11}") String phone_number,
      AddressDto address,
      @Pattern(regexp = "\\w{6,}") String password
) {
}
