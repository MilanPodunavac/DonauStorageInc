package inc.donau.storage.web.rest;

import inc.donau.storage.repository.PersonExtendedRepository;
import inc.donau.storage.repository.PersonRepository;
import inc.donau.storage.service.*;
import inc.donau.storage.service.criteria.BusinessContactCriteria;
import inc.donau.storage.service.criteria.BusinessPartnerCriteria;
import inc.donau.storage.service.criteria.CompanyCriteria;
import inc.donau.storage.service.criteria.EmployeeCriteria;
import inc.donau.storage.service.dto.*;
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
public class PersonExtendedResource extends PersonResource {

    private static final String ENTITY_NAME = "person";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonExtendedService personExtendedService;
    private final PersonExtendedRepository personExtendedRepository;
    private final PersonQueryExtendedService personQueryExtendedService;

    private final EmployeeQueryExtendedService employeeQueryExtendedService;
    private final BusinessContactQueryExtendedService businessContactQueryExtendedService;

    public PersonExtendedResource(
        PersonExtendedService personService,
        PersonExtendedRepository personRepository,
        PersonQueryExtendedService personQueryExtendedService,
        EmployeeQueryExtendedService employeeQueryExtendedService,
        BusinessContactQueryExtendedService businessContactQueryExtendedService
    ) {
        super(personService, personRepository, personQueryExtendedService);
        this.personExtendedService = personService;
        this.personExtendedRepository = personRepository;
        this.personQueryExtendedService = personQueryExtendedService;
        this.employeeQueryExtendedService = employeeQueryExtendedService;
        this.businessContactQueryExtendedService = businessContactQueryExtendedService;
    }

    @Override
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);
        EmployeeCriteria employeeCriteria = new EmployeeCriteria();
        employeeCriteria.setPersonalInfoId(longFilter);
        List<EmployeeDTO> employees = employeeQueryExtendedService.findByCriteria(employeeCriteria);
        if (!employees.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "employeeAsssociationPerson",
                    "An employee " +
                    employees.get(0).getPersonalInfo().getFirstName() +
                    " " +
                    employees.get(0).getPersonalInfo().getLastName() +
                    " (id: " +
                    employees.get(0).getId() +
                    ") " +
                    "is using this personal info, it cannot be deleted"
                )
            )
            .build();

        BusinessContactCriteria businessContactCriteria = new BusinessContactCriteria();
        businessContactCriteria.setPersonalInfoId(longFilter);
        List<BusinessContactDTO> businessContacts = businessContactQueryExtendedService.findByCriteria(businessContactCriteria);
        if (!businessContacts.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "businessContactAsssociationPerson",
                    "A business contact " +
                    businessContacts.get(0).getPersonalInfo().getFirstName() +
                    " " +
                    businessContacts.get(0).getPersonalInfo().getLastName() +
                    " (id: " +
                    businessContacts.get(0).getId() +
                    ") " +
                    "is using this personal info, it cannot be deleted"
                )
            )
            .build();

        return super.deletePerson(id);
    }
}
