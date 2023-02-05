package inc.donau.storage.web.rest;

import inc.donau.storage.repository.LegalEntityExtendedRepository;
import inc.donau.storage.repository.LegalEntityRepository;
import inc.donau.storage.service.*;
import inc.donau.storage.service.criteria.AddressCriteria;
import inc.donau.storage.service.criteria.BusinessPartnerCriteria;
import inc.donau.storage.service.criteria.CompanyCriteria;
import inc.donau.storage.service.dto.AddressDTO;
import inc.donau.storage.service.dto.BusinessPartnerDTO;
import inc.donau.storage.service.dto.CompanyDTO;
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
public class LegalEntityExtendedResource extends LegalEntityResource {

    private static final String ENTITY_NAME = "legalEntity";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LegalEntityExtendedService legalEntityExtendedService;
    private final LegalEntityExtendedRepository legalEntityExtendedRepository;
    private final LegalEntityQueryExtendedService legalEntityQueryExtendedService;

    private final CompanyQueryExtendedService companyQueryExtendedService;
    private final BusinessPartnerQueryExtendedService businessPartnerQueryExtendedService;

    public LegalEntityExtendedResource(
        LegalEntityExtendedService legalEntityService,
        LegalEntityExtendedRepository legalEntityRepository,
        LegalEntityQueryExtendedService legalEntityQueryExtendedService,
        CompanyQueryExtendedService companyQueryExtendedService,
        BusinessPartnerQueryExtendedService businessPartnerQueryExtendedService
    ) {
        super(legalEntityService, legalEntityRepository, legalEntityQueryExtendedService);
        this.legalEntityExtendedService = legalEntityService;
        this.legalEntityExtendedRepository = legalEntityRepository;
        this.legalEntityQueryExtendedService = legalEntityQueryExtendedService;
        this.companyQueryExtendedService = companyQueryExtendedService;
        this.businessPartnerQueryExtendedService = businessPartnerQueryExtendedService;
    }

    @Override
    public ResponseEntity<Void> deleteLegalEntity(@PathVariable Long id) {
        LongFilter longFilter = new LongFilter();
        longFilter.setEquals(id);
        CompanyCriteria companyCriteria = new CompanyCriteria();
        companyCriteria.setLegalEntityInfoId(longFilter);
        List<CompanyDTO> companies = companyQueryExtendedService.findByCriteria(companyCriteria);
        if (!companies.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "companyAsssociationLegalEntity",
                    "A company " +
                    companies.get(0).getLegalEntityInfo().getName() +
                    " (id: " +
                    companies.get(0).getId() +
                    ") " +
                    "is using this legal entity, it cannot be deleted"
                )
            )
            .build();

        BusinessPartnerCriteria businessPartnerCriteria = new BusinessPartnerCriteria();
        businessPartnerCriteria.setLegalEntityInfoId(longFilter);
        List<BusinessPartnerDTO> businessPartners = businessPartnerQueryExtendedService.findByCriteria(businessPartnerCriteria);
        if (!businessPartners.isEmpty()) return ResponseEntity
            .badRequest()
            .headers(
                HeaderUtil.createFailureAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    "businessPartnerAsssociationLegalEntity",
                    "A business partner " +
                    businessPartners.get(0).getLegalEntityInfo().getName() +
                    " (id: " +
                    businessPartners.get(0).getId() +
                    ") " +
                    "is using this legal entity, it cannot be deleted"
                )
            )
            .build();

        return super.deleteLegalEntity(id);
    }
}
