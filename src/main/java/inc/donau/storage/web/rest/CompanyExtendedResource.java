package inc.donau.storage.web.rest;

import inc.donau.storage.repository.CompanyExtendedRepository;
import inc.donau.storage.repository.CompanyRepository;
import inc.donau.storage.service.*;
import inc.donau.storage.service.criteria.*;
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
public class CompanyExtendedResource extends CompanyResource {

    private static final String ENTITY_NAME = "company";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyExtendedService companyExtendedService;
    private final CompanyExtendedRepository companyExtendedRepository;
    private final CompanyQueryExtendedService companyQueryExtendedService;

    private final ResourceQueryExtendedService resourceQueryExtendedService;
    private final BusinessPartnerQueryExtendedService businessYearQueryExtendedService;
    private final EmployeeQueryExtendedService employeeQueryExtendedService;
    private final StorageQueryExtendedService storageQueryExtendedService;

    public CompanyExtendedResource(
        CompanyExtendedService companyService,
        CompanyExtendedRepository companyRepository,
        CompanyQueryExtendedService companyQueryExtendedService,
        ResourceQueryExtendedService resourceQueryExtendedService,
        BusinessPartnerQueryExtendedService businessPartnerQueryExtendedService,
        EmployeeQueryExtendedService employeeQueryExtendedService,
        StorageQueryExtendedService storageQueryExtendedService
    ) {
        super(companyService, companyRepository, companyQueryExtendedService);
        this.companyExtendedService = companyService;
        this.companyExtendedRepository = companyRepository;
        this.companyQueryExtendedService = companyQueryExtendedService;
        this.resourceQueryExtendedService = resourceQueryExtendedService;
        this.businessYearQueryExtendedService = businessPartnerQueryExtendedService;
        this.employeeQueryExtendedService = employeeQueryExtendedService;
        this.storageQueryExtendedService = storageQueryExtendedService;
    }

    @Override
    public ResponseEntity<Void> deleteCompany(@PathVariable Long id) {
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);
        ResourceCriteria resourceCriteria = new ResourceCriteria();
        resourceCriteria.setCompanyId(longFilter);
        List<ResourceDTO> resources = resourceQueryExtendedService.findByCriteria(resourceCriteria);
        if (!resources.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "resourceAssociationCompany",
                    "A resource " +
                    resources.get(0).getName() +
                    " (id: " +
                    resources.get(0).getId() +
                    ") " +
                    "is attached to this company, it cannot be deleted"
                )
            )
            .build();

        BusinessPartnerCriteria businessPartnerCriteria = new BusinessPartnerCriteria();
        businessPartnerCriteria.setCompanyId(longFilter);
        List<BusinessPartnerDTO> businessPartners = businessYearQueryExtendedService.findByCriteria(businessPartnerCriteria);
        if (!businessPartners.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "businessPartnerAssociationCompany",
                    "A business partner " +
                    businessPartners.get(0).getLegalEntityInfo().getName() +
                    " (id: " +
                    businessPartners.get(0).getId() +
                    ") " +
                    "is attached to this company, it cannot be deleted"
                )
            )
            .build();

        EmployeeCriteria employeeCriteria = new EmployeeCriteria();
        employeeCriteria.setCompanyId(longFilter);
        List<EmployeeDTO> employees = employeeQueryExtendedService.findByCriteria(employeeCriteria);
        if (!employees.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "employeeAssociationStorage",
                    "An employee " +
                    employees.get(0).getPersonalInfo().getFirstName() +
                    " " +
                    employees.get(0).getPersonalInfo().getLastName() +
                    " (id: " +
                    employees.get(0).getId() +
                    ") " +
                    "is attached to this company, it cannot be deleted"
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
                    "companyAssociationStorage",
                    "A storage " +
                    storages.get(0).getName() +
                    " (id: " +
                    storages.get(0).getId() +
                    ") " +
                    "is attached to this company, it cannot be deleted"
                )
            )
            .build();

        return super.deleteCompany(id);
    }
}
