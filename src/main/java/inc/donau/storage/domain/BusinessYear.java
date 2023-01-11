package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BusinessYear.
 */
@Entity
@Table(name = "business_year")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessYear implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "year_code", nullable = false)
    private String yearCode;

    @NotNull
    @Column(name = "completed", nullable = false)
    private Boolean completed;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "legalEntityInfo", "resources", "businessPartners", "businessYears", "employees" }, allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BusinessYear id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYearCode() {
        return this.yearCode;
    }

    public BusinessYear yearCode(String yearCode) {
        this.setYearCode(yearCode);
        return this;
    }

    public void setYearCode(String yearCode) {
        this.yearCode = yearCode;
    }

    public Boolean getCompleted() {
        return this.completed;
    }

    public BusinessYear completed(Boolean completed) {
        this.setCompleted(completed);
        return this;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public BusinessYear company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessYear)) {
            return false;
        }
        return id != null && id.equals(((BusinessYear) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessYear{" +
            "id=" + getId() +
            ", yearCode='" + getYearCode() + "'" +
            ", completed='" + getCompleted() + "'" +
            "}";
    }
}
