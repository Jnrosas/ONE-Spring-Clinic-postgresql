package med.voll.api.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserAuthenticationDto(
      @NotBlank
      @Email
      String email,
      @NotBlank
      @Pattern(regexp = "\\w{6,}")
      String password
) {
}
