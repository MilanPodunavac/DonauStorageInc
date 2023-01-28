package inc.donau.storage.service;

import inc.donau.storage.domain.Person;
import inc.donau.storage.repository.ContactInfoExtendedRepository;
import inc.donau.storage.repository.PersonExtendedRepository;
import inc.donau.storage.repository.PersonRepository;
import inc.donau.storage.service.dto.PersonDTO;
import inc.donau.storage.service.mapper.PersonMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PersonExtendedService extends PersonService {

    private final PersonExtendedRepository personExtendedRepository;
    private final ContactInfoExtendedService contactInfoExtendedService;

    public PersonExtendedService(
        PersonExtendedRepository personRepository,
        PersonMapper personMapper,
        ContactInfoExtendedService contactInfoExtendedService
    ) {
        super(personRepository, personMapper);
        this.personExtendedRepository = personRepository;
        this.contactInfoExtendedService = contactInfoExtendedService;
    }

    @Override
    public PersonDTO save(PersonDTO personDTO) {
        if (personDTO.getContactInfo().getId() == null) personDTO.setContactInfo(
            contactInfoExtendedService.save(personDTO.getContactInfo())
        );
        return super.save(personDTO);
    }
}
