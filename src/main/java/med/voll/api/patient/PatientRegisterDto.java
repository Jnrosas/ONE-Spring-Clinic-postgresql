package med.voll.api.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.address.AddressDto;

public record PatientRegisterDto(
      @NotBlank String name,
      @NotBlank String email,
      @NotBlank @Pattern(regexp = "\\d{7,8}") String dni,
      @NotBlank @Pattern(regexp = "\\d{9,11}") String phone_number,
      @NotNull @Valid AddressDto address
) {
}
