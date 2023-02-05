package inc.donau.storage.web.rest;

import inc.donau.storage.domain.Person;
import inc.donau.storage.repository.ContactInfoExtendedRepository;
import inc.donau.storage.repository.ContactInfoRepository;
import inc.donau.storage.service.*;
import inc.donau.storage.service.criteria.LegalEntityCriteria;
import inc.donau.storage.service.criteria.PersonCriteria;
import inc.donau.storage.service.dto.LegalEntityDTO;
import inc.donau.storage.service.dto.PersonDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api/xt")
public class ContactInfoExtendedResource extends ContactInfoResource {

    private static final String ENTITY_NAME = "contactInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContactInfoExtendedService contactInfoExtendedService;
    private final ContactInfoExtendedRepository contactInfoExtendedRepository;
    private final ContactInfoQueryExtendedService contactInfoQueryExtendedService;

    private final PersonQueryExtendedService personQueryExtendedService;
    private final LegalEntityQueryExtendedService legalEntityQueryExtendedService;

    public ContactInfoExtendedResource(
        ContactInfoExtendedService contactInfoService,
        ContactInfoExtendedRepository contactInfoRepository,
        ContactInfoQueryExtendedService contactInfoQueryExtendedService,
        PersonQueryExtendedService personQueryExtendedService,
        LegalEntityQueryExtendedService legalEntityQueryExtendedService
    ) {
        super(contactInfoService, contactInfoRepository, contactInfoQueryExtendedService);
        this.contactInfoExtendedService = contactInfoService;
        this.contactInfoExtendedRepository = contactInfoRepository;
        this.contactInfoQueryExtendedService = contactInfoQueryExtendedService;
        this.personQueryExtendedService = personQueryExtendedService;
        this.legalEntityQueryExtendedService = legalEntityQueryExtendedService;
    }

    @Override
    public ResponseEntity<Void> deleteContactInfo(@PathVariable Long id) {
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);
        PersonCriteria personCriteria = new PersonCriteria();
        personCriteria.setContactInfoId(longFilter);
        List<PersonDTO> people = personQueryExtendedService.findByCriteria(personCriteria);
        if (people.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "contactInfoAssociationPerson",
                    "A person " +
                    people.get(0).getFirstName() +
                    " " +
                    people.get(0).getLastName() +
                    " (id: " +
                    people.get(0).getId() +
                    ") " +
                    "uses this contact info, it cannot be deleted"
                )
            )
            .build();

        LegalEntityCriteria legalEntityCriteria = new LegalEntityCriteria();
        legalEntityCriteria.setContactInfoId(longFilter);
        List<LegalEntityDTO> legalEntities = legalEntityQueryExtendedService.findByCriteria(legalEntityCriteria);
        if (!legalEntities.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "contactInfoAssociationLegalEntity",
                    "A legal entity " +
                    legalEntities.get(0).getName() +
                    " (id: " +
                    legalEntities.get(0).getId() +
                    ") " +
                    "uses this contact info, it cannot be deleted"
                )
            )
            .build();

        return super.deleteContactInfo(id);
    }
}
