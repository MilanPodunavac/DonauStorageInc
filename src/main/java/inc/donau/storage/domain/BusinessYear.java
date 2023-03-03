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
    @JsonIgnoreProperties(
        value = { "legalEntityInfo", "resources", "partners", "businessYears", "employees", "storages" },
        allowSetters = true
    )
    private Company company;

    /**
     * Prevent deletion
     */
    @OneToMany(mappedBy = "businessYear")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "censusItems", "businessYear", "president", "deputy", "censusTaker", "storage" }, allowSetters = true)
    private Set<CensusDocument> censusDocuments = new HashSet<>();

    /**
     * Prevent deletion
     */
    @OneToMany(mappedBy = "businessYear")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "traffic", "businessYear", "resource", "storage" }, allowSetters = true)
    private Set<StorageCard> storageCards = new HashSet<>();

    /**
     * Prevent deletion
     */
    @OneToMany(mappedBy = "businessYear")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "transferDocumentItems", "businessYear", "receivingStorage", "dispatchingStorage", "businessPartner" },
        allowSetters = true
    )
    private Set<TransferDocument> transfers = new HashSet<>();

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

    public Set<CensusDocument> getCensusDocuments() {
        return this.censusDocuments;
    }

    public void setCensusDocuments(Set<CensusDocument> censusDocuments) {
        if (this.censusDocuments != null) {
            this.censusDocuments.forEach(i -> i.setBusinessYear(null));
        }
        if (censusDocuments != null) {
            censusDocuments.forEach(i -> i.setBusinessYear(this));
        }
        this.censusDocuments = censusDocuments;
    }

    public BusinessYear censusDocuments(Set<CensusDocument> censusDocuments) {
        this.setCensusDocuments(censusDocuments);
        return this;
    }

    public BusinessYear addCensusDocuments(CensusDocument censusDocument) {
        this.censusDocuments.add(censusDocument);
        censusDocument.setBusinessYear(this);
        return this;
    }

    public BusinessYear removeCensusDocuments(CensusDocument censusDocument) {
        this.censusDocuments.remove(censusDocument);
        censusDocument.setBusinessYear(null);
        return this;
    }

    public Set<StorageCard> getStorageCards() {
        return this.storageCards;
    }

    public void setStorageCards(Set<StorageCard> storageCards) {
        if (this.storageCards != null) {
            this.storageCards.forEach(i -> i.setBusinessYear(null));
        }
        if (storageCards != null) {
            storageCards.forEach(i -> i.setBusinessYear(this));
        }
        this.storageCards = storageCards;
    }

    public BusinessYear storageCards(Set<StorageCard> storageCards) {
        this.setStorageCards(storageCards);
        return this;
    }

    public BusinessYear addStorageCards(StorageCard storageCard) {
        this.storageCards.add(storageCard);
        storageCard.setBusinessYear(this);
        return this;
    }

    public BusinessYear removeStorageCards(StorageCard storageCard) {
        this.storageCards.remove(storageCard);
        storageCard.setBusinessYear(null);
        return this;
    }

    public Set<TransferDocument> getTransfers() {
        return this.transfers;
    }

    public void setTransfers(Set<TransferDocument> transferDocuments) {
        if (this.transfers != null) {
            this.transfers.forEach(i -> i.setBusinessYear(null));
        }
        if (transferDocuments != null) {
            transferDocuments.forEach(i -> i.setBusinessYear(this));
        }
        this.transfers = transferDocuments;
    }

    public BusinessYear transfers(Set<TransferDocument> transferDocuments) {
        this.setTransfers(transferDocuments);
        return this;
    }

    public BusinessYear addTransfers(TransferDocument transferDocument) {
        this.transfers.add(transferDocument);
        transferDocument.setBusinessYear(this);
        return this;
    }

    public BusinessYear removeTransfers(TransferDocument transferDocument) {
        this.transfers.remove(transferDocument);
        transferDocument.setBusinessYear(null);
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
