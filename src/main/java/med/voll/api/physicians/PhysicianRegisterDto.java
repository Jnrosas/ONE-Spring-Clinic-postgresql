package med.voll.api.physicians;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.address.AddressDto;

public record PhysicianRegisterDto(
      @NotBlank String name,
      @NotBlank @Email String email,
      @NotBlank @Pattern(regexp = "\\d{7,8}") String dni,
      @NotBlank @Pattern(regexp = "\\d{9,11}") String phone_number,
      @NotNull SpecialtyEnum specialty,
      @NotNull @Valid AddressDto address
) {
}
