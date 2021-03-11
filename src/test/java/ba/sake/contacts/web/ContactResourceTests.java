package ba.sake.contacts.web;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import ba.sake.contacts.db.entities.ContactEntity;
import ba.sake.contacts.web.models.CreateAddressRequest;
import ba.sake.contacts.web.models.CreateContactRequest;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContactResourceTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void nonExistingUrlShouldReturn404() throws Exception {
        var response = restTemplate.getForEntity(apiUrl("/nope"), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void invalidCreateShouldReturnBadRequest() throws Exception {
        var request = new CreateContactRequest();
        request.setFullName("Test"); // missing address field ...
        var response = restTemplate.postForEntity(apiUrl("/contacts"), request, null);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void createShouldCreateContact() throws Exception {

        var request = generateContact("test");
        var response = restTemplate.postForEntity(apiUrl("/contacts"), request, ContactEntity.class);
        var createdContact = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createdContact.getFullName()).isEqualTo(request.getFullName());
        assertThat(createdContact.getAddress().getCity()).isEqualTo(request.getAddress().getCity());
    }

    @Test
    public void searchShouldFilterContacts() throws Exception {

        var request1 = generateContact("test1");
        restTemplate.postForEntity(apiUrl("/contacts"), request1, ContactEntity.class);
        var request2 = generateContact("test2");
        restTemplate.postForEntity(apiUrl("/contacts"), request2, ContactEntity.class);

        var response = restTemplate.exchange(apiUrl("/contacts/search?postalCode=test2"), HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ContactEntity>>() {});
        assertThat(response.getBody().size()).isEqualTo(1);
        assertThat(response.getBody().get(0).getAddress().getPostalCode()).isEqualTo("test2");
    }

    private CreateContactRequest generateContact(String postalCode) {
        var request = new CreateContactRequest();
        request.setFullName("test");
        request.setDateOfBirth(LocalDate.of(1990, 5, 5));
        CreateAddressRequest address = new CreateAddressRequest();
        address.setCity("testCity");
        address.setPostalCode(postalCode);
        request.setAddress(address);
        return request;
    }

    private String apiUrl(String other) {
        return "http://localhost:" + port + other;
    }

}
