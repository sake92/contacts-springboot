package ba.sake.contacts.web;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ba.sake.contacts.db.entities.ContactEntity;
import ba.sake.contacts.services.ContactService;
import ba.sake.contacts.web.models.CreateContactRequest;

@RequestMapping("/contacts")
@RestController
public class ContactResource {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ContactEntity create(@Valid @RequestBody CreateContactRequest request) {
        return contactService.create(request);
    }

    @GetMapping
    public List<ContactEntity> findAll() {
        return contactService.findAll();
    }

    @GetMapping("/search")
    public List<ContactEntity> findByPostalCode(@RequestParam @NotBlank String postalCode) {
        return contactService.findByPostalCode(postalCode);
    }

}
