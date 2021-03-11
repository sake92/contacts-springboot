package ba.sake.contacts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ContactsApplication {

	@SuppressWarnings("resource")
    public static void main(String[] args) {
		SpringApplication.run(ContactsApplication.class, args);
	}

}
