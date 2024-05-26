package med.voll.api.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
   private String street;
   private String city;
   private String po_box;

   public Address(AddressDto address) {
      this.street = address.street();
      this.city = address.city();
      this.po_box = address.po_box();
   }

   public Address updateAddress(AddressDto address) {
      this.street = address.street();
      this.city = address.city();
      this.po_box = address.po_box();
      return this;
   }
}
