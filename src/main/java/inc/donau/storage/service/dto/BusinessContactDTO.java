package inc.donau.storage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link inc.donau.storage.domain.BusinessContact} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessContactDTO implements Serializable {

    private Long id;

    private PersonDTO personalInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonDTO getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonDTO personalInfo) {
        this.personalInfo = personalInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessContactDTO)) {
            return false;
        }

        BusinessContactDTO businessContactDTO = (BusinessContactDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, businessContactDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessContactDTO{" +
            "id=" + getId() +
            ", personalInfo=" + getPersonalInfo() +
            "}";
    }
}
