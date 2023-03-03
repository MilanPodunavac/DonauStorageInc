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
 * sr: Magacin
 */
@Entity
@Table(name = "storage")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Storage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * AutoNumber
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Cascade delete
     */
    @JsonIgnoreProperties(value = { "city", "employee", "legalEntity", "storage" }, allowSetters = true)
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Address address;

    /**
     * Prevent deletion
     */
    @OneToMany(mappedBy = "storage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "traffic", "businessYear", "resource", "storage" }, allowSetters = true)
    private Set<StorageCard> storageCards = new HashSet<>();

    @OneToMany(mappedBy = "receivingStorage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "transferDocumentItems", "businessYear", "receivingStorage", "dispatchingStorage", "businessPartner" },
        allowSetters = true
    )
    private Set<TransferDocument> receiveds = new HashSet<>();

    @OneToMany(mappedBy = "dispatchingStorage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "transferDocumentItems", "businessYear", "receivingStorage", "dispatchingStorage", "businessPartner" },
        allowSetters = true
    )
    private Set<TransferDocument> dispatcheds = new HashSet<>();

    @OneToMany(mappedBy = "storage")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "censusItems", "businessYear", "president", "deputy", "censusTaker", "storage" }, allowSetters = true)
    private Set<CensusDocument> censusDocuments = new HashSet<>();

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

    public Storage id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Storage code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Storage name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Storage address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Set<StorageCard> getStorageCards() {
        return this.storageCards;
    }

    public void setStorageCards(Set<StorageCard> storageCards) {
        if (this.storageCards != null) {
            this.storageCards.forEach(i -> i.setStorage(null));
        }
        if (storageCards != null) {
            storageCards.forEach(i -> i.setStorage(this));
        }
        this.storageCards = storageCards;
    }

    public Storage storageCards(Set<StorageCard> storageCards) {
        this.setStorageCards(storageCards);
        return this;
    }

    public Storage addStorageCards(StorageCard storageCard) {
        this.storageCards.add(storageCard);
        storageCard.setStorage(this);
        return this;
    }

    public Storage removeStorageCards(StorageCard storageCard) {
        this.storageCards.remove(storageCard);
        storageCard.setStorage(null);
        return this;
    }

    public Set<TransferDocument> getReceiveds() {
        return this.receiveds;
    }

    public void setReceiveds(Set<TransferDocument> transferDocuments) {
        if (this.receiveds != null) {
            this.receiveds.forEach(i -> i.setReceivingStorage(null));
        }
        if (transferDocuments != null) {
            transferDocuments.forEach(i -> i.setReceivingStorage(this));
        }
        this.receiveds = transferDocuments;
    }

    public Storage receiveds(Set<TransferDocument> transferDocuments) {
        this.setReceiveds(transferDocuments);
        return this;
    }

    public Storage addReceived(TransferDocument transferDocument) {
        this.receiveds.add(transferDocument);
        transferDocument.setReceivingStorage(this);
        return this;
    }

    public Storage removeReceived(TransferDocument transferDocument) {
        this.receiveds.remove(transferDocument);
        transferDocument.setReceivingStorage(null);
        return this;
    }

    public Set<TransferDocument> getDispatcheds() {
        return this.dispatcheds;
    }

    public void setDispatcheds(Set<TransferDocument> transferDocuments) {
        if (this.dispatcheds != null) {
            this.dispatcheds.forEach(i -> i.setDispatchingStorage(null));
        }
        if (transferDocuments != null) {
            transferDocuments.forEach(i -> i.setDispatchingStorage(this));
        }
        this.dispatcheds = transferDocuments;
    }

    public Storage dispatcheds(Set<TransferDocument> transferDocuments) {
        this.setDispatcheds(transferDocuments);
        return this;
    }

    public Storage addDispatched(TransferDocument transferDocument) {
        this.dispatcheds.add(transferDocument);
        transferDocument.setDispatchingStorage(this);
        return this;
    }

    public Storage removeDispatched(TransferDocument transferDocument) {
        this.dispatcheds.remove(transferDocument);
        transferDocument.setDispatchingStorage(null);
        return this;
    }

    public Set<CensusDocument> getCensusDocuments() {
        return this.censusDocuments;
    }

    public void setCensusDocuments(Set<CensusDocument> censusDocuments) {
        if (this.censusDocuments != null) {
            this.censusDocuments.forEach(i -> i.setStorage(null));
        }
        if (censusDocuments != null) {
            censusDocuments.forEach(i -> i.setStorage(this));
        }
        this.censusDocuments = censusDocuments;
    }

    public Storage censusDocuments(Set<CensusDocument> censusDocuments) {
        this.setCensusDocuments(censusDocuments);
        return this;
    }

    public Storage addCensusDocument(CensusDocument censusDocument) {
        this.censusDocuments.add(censusDocument);
        censusDocument.setStorage(this);
        return this;
    }

    public Storage removeCensusDocument(CensusDocument censusDocument) {
        this.censusDocuments.remove(censusDocument);
        censusDocument.setStorage(null);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Storage company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Storage)) {
            return false;
        }
        return id != null && id.equals(((Storage) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Storage{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
