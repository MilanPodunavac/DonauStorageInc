package inc.donau.storage.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link inc.donau.storage.domain.BusinessYear} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessYearDTO implements Serializable {

    private Long id;

    @NotNull
    private String yearCode;

    @NotNull
    private Boolean completed;

    private CompanyDTO company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYearCode() {
        return yearCode;
    }

    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
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
        if (!(o instanceof BusinessYearDTO)) {
            return false;
        }

        BusinessYearDTO businessYearDTO = (BusinessYearDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, businessYearDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessYearDTO{" +
            "id=" + getId() +
            ", yearCode='" + getYearCode() + "'" +
            ", completed='" + getCompleted() + "'" +
            ", company=" + getCompany() +
            "}";
    }
}
