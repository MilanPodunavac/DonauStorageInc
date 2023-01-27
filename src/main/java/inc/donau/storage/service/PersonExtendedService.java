package inc.donau.storage.service;

import inc.donau.storage.repository.PersonExtendedRepository;
import inc.donau.storage.repository.PersonRepository;
import inc.donau.storage.service.mapper.PersonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonExtendedService extends PersonService {

    private final PersonExtendedRepository personExtendedRepository;

    public PersonExtendedService(PersonExtendedRepository personRepository, PersonMapper personMapper) {
        super(personRepository, personMapper);
        this.personExtendedRepository = personRepository;
    }
}
