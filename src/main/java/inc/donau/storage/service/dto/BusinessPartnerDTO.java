package inc.donau.storage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link inc.donau.storage.domain.BusinessPartner} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessPartnerDTO implements Serializable {

    private Long id;

    private BusinessContactDTO businessContact;

    private LegalEntityDTO legalEntityInfo;

    private CompanyDTO company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessContactDTO getBusinessContact() {
        return businessContact;
    }

    public void setBusinessContact(BusinessContactDTO businessContact) {
        this.businessContact = businessContact;
    }

    public LegalEntityDTO getLegalEntityInfo() {
        return legalEntityInfo;
    }

    public void setLegalEntityInfo(LegalEntityDTO legalEntityInfo) {
        this.legalEntityInfo = legalEntityInfo;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessPartnerDTO)) {
            return false;
        }

        BusinessPartnerDTO businessPartnerDTO = (BusinessPartnerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, businessPartnerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessPartnerDTO{" +
            "id=" + getId() +
            ", businessContact=" + getBusinessContact() +
            ", legalEntityInfo=" + getLegalEntityInfo() +
            ", company=" + getCompany() +
            "}";
    }
}
