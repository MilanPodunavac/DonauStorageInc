package inc.donau.storage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link inc.donau.storage.domain.LegalEntity} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LegalEntityDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    /**
     * sr: Poreski identifikacioni broj (PIB)
     */
    @NotNull
    @Pattern(regexp = "[0-9]{10}")
    @Schema(description = "sr: Poreski identifikacioni broj (PIB)", required = true)
    private String taxIdentificationNumber;

    /**
     * sr: Maticni broj (MB)
     */
    @NotNull
    @Pattern(regexp = "[0-9]{8}")
    @Schema(description = "sr: Maticni broj (MB)", required = true)
    private String identificationNumber;

    private ContactInfoDTO contactInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public ContactInfoDTO getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ContactInfoDTO contactInfo) {
        this.contactInfo = contactInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LegalEntityDTO)) {
            return false;
        }

        LegalEntityDTO legalEntityDTO = (LegalEntityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, legalEntityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LegalEntityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", taxIdentificationNumber='" + getTaxIdentificationNumber() + "'" +
            ", identificationNumber='" + getIdentificationNumber() + "'" +
            ", contactInfo=" + getContactInfo() +
            "}";
    }
}
