package ba.sake.contacts.web.models;

import java.time.LocalDate;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateContactRequest {

    @NotBlank
    private String fullName;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    @Valid
    private CreateAddressRequest address;
}
