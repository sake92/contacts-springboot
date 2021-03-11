package ba.sake.contacts.web.models;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAddressRequest {

    @NotBlank
    private String city;

    @NotBlank
    private String postalCode;
}
