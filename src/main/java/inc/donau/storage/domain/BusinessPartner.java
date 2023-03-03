package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BusinessPartner.
 */
@Entity
@Table(name = "business_partner")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessPartner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * Cascade delete
     */
    @JsonIgnoreProperties(value = { "personalInfo", "businessPartner" }, allowSetters = true)
    @OneToOne(optional = false, cascade = CascadeType.REMOVE)
    @NotNull
    @JoinColumn(unique = true)
    private BusinessContact businessContact;

    /**
     * Cascade delete
     */
    @JsonIgnoreProperties(value = { "contactInfo", "address", "legalEntity", "company" }, allowSetters = true)
    @OneToOne(optional = false, cascade = CascadeType.REMOVE)
    @NotNull
    @JoinColumn(unique = true)
    private LegalEntity legalEntityInfo;

    @OneToMany(mappedBy = "businessPartner")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "transferDocumentItems", "businessYear", "receivingStorage", "dispatchingStorage", "businessPartner" },
        allowSetters = true
    )
    private Set<TransferDocument> transfers = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "legalEntityInfo", "resources", "partners", "businessYears", "employees", "storages" },
        allowSetters = true
    )
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BusinessPartner id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessContact getBusinessContact() {
        return this.businessContact;
    }

    public void setBusinessContact(BusinessContact businessContact) {
        this.businessContact = businessContact;
    }

    public BusinessPartner businessContact(BusinessContact businessContact) {
        this.setBusinessContact(businessContact);
        return this;
    }

    public LegalEntity getLegalEntityInfo() {
        return this.legalEntityInfo;
    }

    public void setLegalEntityInfo(LegalEntity legalEntity) {
        this.legalEntityInfo = legalEntity;
    }

    public BusinessPartner legalEntityInfo(LegalEntity legalEntity) {
        this.setLegalEntityInfo(legalEntity);
        return this;
    }

    public Set<TransferDocument> getTransfers() {
        return this.transfers;
    }

    public void setTransfers(Set<TransferDocument> transferDocuments) {
        if (this.transfers != null) {
            this.transfers.forEach(i -> i.setBusinessPartner(null));
        }
        if (transferDocuments != null) {
            transferDocuments.forEach(i -> i.setBusinessPartner(this));
        }
        this.transfers = transferDocuments;
    }

    public BusinessPartner transfers(Set<TransferDocument> transferDocuments) {
        this.setTransfers(transferDocuments);
        return this;
    }

    public BusinessPartner addTransfers(TransferDocument transferDocument) {
        this.transfers.add(transferDocument);
        transferDocument.setBusinessPartner(this);
        return this;
    }

    public BusinessPartner removeTransfers(TransferDocument transferDocument) {
        this.transfers.remove(transferDocument);
        transferDocument.setBusinessPartner(null);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public BusinessPartner company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessPartner)) {
            return false;
        }
        return id != null && id.equals(((BusinessPartner) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessPartner{" +
            "id=" + getId() +
            "}";
    }
}
