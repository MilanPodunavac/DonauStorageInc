package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LegalEntity.
 */
@Entity
@Table(name = "legal_entity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LegalEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * sr: Poreski identifikacioni broj (PIB)
     */
    @NotNull
    @Pattern(regexp = "[0-9]{10}")
    @Column(name = "tax_identification_number", nullable = false, unique = true)
    private String taxIdentificationNumber;

    /**
     * sr: Maticni broj (MB)
     */
    @NotNull
    @Pattern(regexp = "[0-9]{8}")
    @Column(name = "identification_number", nullable = false, unique = true)
    private String identificationNumber;

    /**
     * Email and phone number\nCascade delete
     */
    @JsonIgnoreProperties(value = { "person", "legalEntity" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private ContactInfo contactInfo;

    /**
     * Cascade delete
     */
    @JsonIgnoreProperties(value = { "city", "employee", "legalEntity", "storage" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Address address;

    /**
     * Prevent delete
     */
    @JsonIgnoreProperties(value = { "businessContact", "legalEntityInfo", "transfers", "company" }, allowSetters = true)
    @OneToOne(mappedBy = "legalEntityInfo")
    private BusinessPartner businessPartner;

    /**
     * Prevent delete
     */
    @JsonIgnoreProperties(
        value = { "legalEntityInfo", "resources", "partners", "businessYears", "employees", "storages" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "legalEntityInfo")
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LegalEntity id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public LegalEntity name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTaxIdentificationNumber() {
        return this.taxIdentificationNumber;
    }

    public LegalEntity taxIdentificationNumber(String taxIdentificationNumber) {
        this.setTaxIdentificationNumber(taxIdentificationNumber);
        return this;
    }

    public void setTaxIdentificationNumber(String taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public String getIdentificationNumber() {
        return this.identificationNumber;
    }

    public LegalEntity identificationNumber(String identificationNumber) {
        this.setIdentificationNumber(identificationNumber);
        return this;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public ContactInfo getContactInfo() {
        return this.contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public LegalEntity contactInfo(ContactInfo contactInfo) {
        this.setContactInfo(contactInfo);
        return this;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LegalEntity address(Address address) {
        this.setAddress(address);
        return this;
    }

    public BusinessPartner getBusinessPartner() {
        return this.businessPartner;
    }

    public void setBusinessPartner(BusinessPartner businessPartner) {
        if (this.businessPartner != null) {
            this.businessPartner.setLegalEntityInfo(null);
        }
        if (businessPartner != null) {
            businessPartner.setLegalEntityInfo(this);
        }
        this.businessPartner = businessPartner;
    }

    public LegalEntity businessPartner(BusinessPartner businessPartner) {
        this.setBusinessPartner(businessPartner);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        if (this.company != null) {
            this.company.setLegalEntityInfo(null);
        }
        if (company != null) {
            company.setLegalEntityInfo(this);
        }
        this.company = company;
    }

    public LegalEntity company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LegalEntity)) {
            return false;
        }
        return id != null && id.equals(((LegalEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LegalEntity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", taxIdentificationNumber='" + getTaxIdentificationNumber() + "'" +
            ", identificationNumber='" + getIdentificationNumber() + "'" +
            "}";
    }
}
