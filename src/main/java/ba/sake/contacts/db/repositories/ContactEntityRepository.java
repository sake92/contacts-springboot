package ba.sake.contacts.db.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ba.sake.contacts.db.entities.ContactEntity;

@Repository
public interface ContactEntityRepository extends JpaRepository<ContactEntity, Long> {

    List<ContactEntity> findByAddress_PostalCode(String postalCode);
}
