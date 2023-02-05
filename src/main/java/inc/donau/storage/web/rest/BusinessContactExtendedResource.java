package inc.donau.storage.web.rest;

import inc.donau.storage.repository.BusinessContactExtendedRepository;
import inc.donau.storage.repository.BusinessContactRepository;
import inc.donau.storage.service.BusinessContactExtendedService;
import inc.donau.storage.service.BusinessContactQueryExtendedService;
import inc.donau.storage.service.BusinessContactService;
import inc.donau.storage.service.BusinessPartnerQueryExtendedService;
import inc.donau.storage.service.criteria.BusinessPartnerCriteria;
import inc.donau.storage.service.criteria.EmployeeCriteria;
import inc.donau.storage.service.criteria.LegalEntityCriteria;
import inc.donau.storage.service.criteria.StorageCriteria;
import inc.donau.storage.service.dto.BusinessPartnerDTO;
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
public class BusinessContactExtendedResource extends BusinessContactResource {

    private static final String ENTITY_NAME = "businessContact";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessContactExtendedService businessContactExtendedService;
    private final BusinessContactExtendedRepository businessContactExtendedRepository;
    private final BusinessContactQueryExtendedService businessContactQueryExtendedService;

    private final BusinessPartnerQueryExtendedService businessPartnerQueryExtendedService;

    public BusinessContactExtendedResource(
        BusinessContactExtendedService businessContactService,
        BusinessContactExtendedRepository businessContactRepository,
        BusinessContactQueryExtendedService businessContactQueryExtendedService,
        BusinessPartnerQueryExtendedService businessPartnerQueryExtendedService
    ) {
        super(businessContactService, businessContactRepository, businessContactQueryExtendedService);
        this.businessContactExtendedService = businessContactService;
        this.businessContactExtendedRepository = businessContactRepository;
        this.businessContactQueryExtendedService = businessContactQueryExtendedService;
        this.businessPartnerQueryExtendedService = businessPartnerQueryExtendedService;
    }

    @Override
    public ResponseEntity<Void> deleteBusinessContact(@PathVariable Long id) {
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);
        BusinessPartnerCriteria businessPartnerCriteria = new BusinessPartnerCriteria();
        businessPartnerCriteria.setBusinessContactId(longFilter);
        List<BusinessPartnerDTO> businessPartners = businessPartnerQueryExtendedService.findByCriteria(businessPartnerCriteria);
        if (!businessPartners.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "businessContactAsssociationBusinessPartner",
                    "A business partner " +
                    businessPartners.get(0).getLegalEntityInfo().getName() +
                    " (id: " +
                    businessPartners.get(0).getId() +
                    ") " +
                    "has this business contact, it cannot be deleted"
                )
            )
            .build();

        return super.deleteBusinessContact(id);
    }
}
