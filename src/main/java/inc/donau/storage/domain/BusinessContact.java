package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BusinessContact.
 */
@Entity
@Table(name = "business_contact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * Cascade delete
     */
    @JsonIgnoreProperties(value = { "contactInfo", "businessContact", "employee" }, allowSetters = true)
    @OneToOne(optional = false, cascade = CascadeType.REMOVE)
    @NotNull
    @JoinColumn(unique = true)
    private Person personalInfo;

    /**
     * Prevent delete
     */
    @JsonIgnoreProperties(value = { "businessContact", "legalEntityInfo", "transfers", "company" }, allowSetters = true)
    @OneToOne(mappedBy = "businessContact")
    private BusinessPartner businessPartner;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BusinessContact id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPersonalInfo() {
        return this.personalInfo;
    }

    public void setPersonalInfo(Person person) {
        this.personalInfo = person;
    }

    public BusinessContact personalInfo(Person person) {
        this.setPersonalInfo(person);
        return this;
    }

    public BusinessPartner getBusinessPartner() {
        return this.businessPartner;
    }

    public void setBusinessPartner(BusinessPartner businessPartner) {
        if (this.businessPartner != null) {
            this.businessPartner.setBusinessContact(null);
        }
        if (businessPartner != null) {
            businessPartner.setBusinessContact(this);
        }
        this.businessPartner = businessPartner;
    }

    public BusinessContact businessPartner(BusinessPartner businessPartner) {
        this.setBusinessPartner(businessPartner);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessContact)) {
            return false;
        }
        return id != null && id.equals(((BusinessContact) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessContact{" +
            "id=" + getId() +
            "}";
    }
}
