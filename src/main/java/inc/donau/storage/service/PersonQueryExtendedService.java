package inc.donau.storage.service;

import inc.donau.storage.repository.PersonExtendedRepository;
import inc.donau.storage.repository.PersonRepository;
import inc.donau.storage.service.mapper.PersonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonQueryExtendedService extends PersonQueryService {

    private final PersonExtendedRepository personExtendedRepository;

    public PersonQueryExtendedService(PersonExtendedRepository personRepository, PersonMapper personMapper) {
        super(personRepository, personMapper);
        this.personExtendedRepository = personRepository;
    }
}
