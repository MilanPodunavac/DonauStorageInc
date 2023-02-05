package inc.donau.storage.web.rest;

import inc.donau.storage.domain.Storage;
import inc.donau.storage.repository.AddressExtendedRepository;
import inc.donau.storage.repository.AddressRepository;
import inc.donau.storage.service.*;
import inc.donau.storage.service.criteria.EmployeeCriteria;
import inc.donau.storage.service.criteria.LegalEntityCriteria;
import inc.donau.storage.service.criteria.PersonCriteria;
import inc.donau.storage.service.criteria.StorageCriteria;
import inc.donau.storage.service.dto.EmployeeDTO;
import inc.donau.storage.service.dto.LegalEntityDTO;
import inc.donau.storage.service.dto.StorageDTO;
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
public class AddressExtendedResource extends AddressResource {

    private static final String ENTITY_NAME = "address";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AddressExtendedService addressExtendedService;
    private final AddressExtendedRepository addressExtendedRepository;
    private final AddressQueryExtendedService addressQueryExtendedService;

    private final EmployeeQueryExtendedService employeeQueryExtendedService;
    private final LegalEntityQueryExtendedService legalEntityQueryExtendedService;
    private final StorageQueryExtendedService storageQueryExtendedService;

    public AddressExtendedResource(
        AddressExtendedService addressService,
        AddressExtendedRepository addressRepository,
        AddressQueryExtendedService addressQueryExtendedService,
        EmployeeQueryExtendedService employeeQueryExtendedService,
        LegalEntityQueryExtendedService legalEntityQueryExtendedService,
        StorageQueryExtendedService storageQueryExtendedService
    ) {
        super(addressService, addressRepository, addressQueryExtendedService);
        this.addressExtendedService = addressService;
        this.addressExtendedRepository = addressRepository;
        this.addressQueryExtendedService = addressQueryExtendedService;
        this.employeeQueryExtendedService = employeeQueryExtendedService;
        this.legalEntityQueryExtendedService = legalEntityQueryExtendedService;
        this.storageQueryExtendedService = storageQueryExtendedService;
    }

    @Override
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);
        EmployeeCriteria employeeCriteria = new EmployeeCriteria();
        employeeCriteria.setAddressId(longFilter);
        List<EmployeeDTO> employees = employeeQueryExtendedService.findByCriteria(employeeCriteria);
        if (!employees.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "addressAssociationEmployee",
                    "An employee " +
                    employees.get(0).getPersonalInfo().getFirstName() +
                    " " +
                    employees.get(0).getPersonalInfo().getLastName() +
                    " (id: " +
                    employees.get(0).getId() +
                    ") " +
                    "uses this address, it cannot be deleted"
                )
            )
            .build();

        LegalEntityCriteria legalEntityCriteria = new LegalEntityCriteria();
        legalEntityCriteria.setAddressId(longFilter);
        List<LegalEntityDTO> legalEntities = legalEntityQueryExtendedService.findByCriteria(legalEntityCriteria);
        if (!legalEntities.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "addressAssociationLegalEntity",
                    "A legal entity " +
                    legalEntities.get(0).getName() +
                    " (id: " +
                    legalEntities.get(0).getId() +
                    ") " +
                    "uses this address, it cannot be deleted"
                )
            )
            .build();

        StorageCriteria storageCriteria = new StorageCriteria();
        storageCriteria.setAddressId(longFilter);
        List<StorageDTO> storages = storageQueryExtendedService.findByCriteria(storageCriteria);
        if (!storages.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "addressAssociationStorage",
                    "A storage " +
                    storages.get(0).getName() +
                    " (id: " +
                    storages.get(0).getId() +
                    ") " +
                    "uses this address, it cannot be deleted"
                )
            )
            .build();

        return super.deleteAddress(id);
    }
}
