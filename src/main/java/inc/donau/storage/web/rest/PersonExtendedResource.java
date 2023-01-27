package inc.donau.storage.web.rest;

import inc.donau.storage.repository.PersonExtendedRepository;
import inc.donau.storage.repository.PersonRepository;
import inc.donau.storage.service.PersonExtendedService;
import inc.donau.storage.service.PersonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/xt")
public class PersonExtendedResource extends PersonResource {

    private final PersonExtendedService personExtendedService;
    private final PersonExtendedRepository personExtendedRepository;

    public PersonExtendedResource(PersonExtendedService personService, PersonExtendedRepository personRepository) {
        super(personService, personRepository);
        this.personExtendedService = personService;
        this.personExtendedRepository = personRepository;
    }
}
