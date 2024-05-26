package med.voll.api.address;

import jakarta.validation.constraints.NotBlank;

public record AddressDto(
      @NotBlank String street,
      @NotBlank String city,
      @NotBlank String po_box
) {
}
