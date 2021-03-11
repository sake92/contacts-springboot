package ba.sake.contacts.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ba.sake.contacts.db.entities.AddressEntity;
import ba.sake.contacts.db.entities.ContactEntity;
import ba.sake.contacts.db.repositories.ContactEntityRepository;
import ba.sake.contacts.web.models.CreateAddressRequest;
import ba.sake.contacts.web.models.CreateContactRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class ContactService {

    @Autowired
    private ContactEntityRepository contactEntityRepository;

    public List<ContactEntity> findAll() {
        return contactEntityRepository.findAll();
    }

    public List<ContactEntity> findByPostalCode(String postalCode) {
        return contactEntityRepository.findByAddress_PostalCode(postalCode);
    }

    public ContactEntity create(CreateContactRequest request) {
        var contactEntity = requets2ContactEntity(request);
        ContactEntity newContactEntity = contactEntityRepository.save(contactEntity);
        log.info("New contact created. Id = {}", newContactEntity.getId());
        return newContactEntity;
    }

    /* helpers */
    private ContactEntity requets2ContactEntity(CreateContactRequest request) {
        var addressEntity = request2AddressEntity(request.getAddress());
        var contactEntity = new ContactEntity();
        contactEntity.setFullName(request.getFullName());
        contactEntity.setDateOfBirth(request.getDateOfBirth());
        contactEntity.setAddress(addressEntity);
        return contactEntity;
    }

    private AddressEntity request2AddressEntity(CreateAddressRequest createAddressRequest) {
        var addressEntity = new AddressEntity();
        addressEntity.setCity(createAddressRequest.getCity());
        addressEntity.setPostalCode(createAddressRequest.getPostalCode());
        return addressEntity;
    }
}
