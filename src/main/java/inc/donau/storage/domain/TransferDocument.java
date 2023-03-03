package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import inc.donau.storage.domain.enumeration.TransferDocumentStatus;
import inc.donau.storage.domain.enumeration.TransferDocumentType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * sr: Prometni dokument
 */
@Entity
@Table(name = "transfer_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransferDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransferDocumentType type;

    @NotNull
    @Column(name = "transfer_date", nullable = false)
    private LocalDate transferDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TransferDocumentStatus status;

    /**
     * sr: Knjizenje
     */
    @Column(name = "accounting_date")
    private LocalDate accountingDate;

    /**
     * sr: Storniranje
     */
    @Column(name = "reversal_date")
    private LocalDate reversalDate;

    @OneToMany(mappedBy = "transferDocument", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "transferDocument", "resource" }, allowSetters = true)
    private Set<TransferDocumentItem> transferDocumentItems = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "company", "censusDocuments", "storageCards", "transfers" }, allowSetters = true)
    private BusinessYear businessYear;

    /**
     * en: Storage that receives goods (if RECEIVING or INTERSTORAGE it is NOT NULL, if DISPATCHING it is NULL)\nsr: Magacin koji prima robu (u slucaju primke i medjumagacinskog poslovanja NOT NULL, u slucaju otpremnice NULL)
     */
    @ManyToOne
    @JsonIgnoreProperties(
        value = { "address", "storageCards", "receiveds", "dispatcheds", "censusDocuments", "company" },
        allowSetters = true
    )
    private Storage receivingStorage;

    /**
     * en: Storage that receives goods (if DISPATCHING or INTERSTORAGE it is NOT NULL, if RECEIVING it is NULL)\nsr: Magacin koji otprema robu (u slucaju otpremnice i medjumagacinskog poslovanja NOT NULL, u slucaju primke NULL)
     */
    @ManyToOne
    @JsonIgnoreProperties(
        value = { "address", "storageCards", "receiveds", "dispatcheds", "censusDocuments", "company" },
        allowSetters = true
    )
    private Storage dispatchingStorage;

    /**
     * en: Storage that receives goods (if RECEIVING or DISPATCHING it is NOT NULL, if INTERSTORAGE it is NULL)\nsr: Poslovni partner koji prima ili otprema robu (u slucaju primke i otpremnice NOT NULL, u slucaju medjumagacinskog poslovanja NULL)
     */
    @ManyToOne
    @JsonIgnoreProperties(value = { "businessContact", "legalEntityInfo", "transfers", "company" }, allowSetters = true)
    private BusinessPartner businessPartner;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TransferDocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransferDocumentType getType() {
        return this.type;
    }

    public TransferDocument type(TransferDocumentType type) {
        this.setType(type);
        return this;
    }

    public void setType(TransferDocumentType type) {
        this.type = type;
    }

    public LocalDate getTransferDate() {
        return this.transferDate;
    }

    public TransferDocument transferDate(LocalDate transferDate) {
        this.setTransferDate(transferDate);
        return this;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    public TransferDocumentStatus getStatus() {
        return this.status;
    }

    public TransferDocument status(TransferDocumentStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(TransferDocumentStatus status) {
        this.status = status;
    }

    public LocalDate getAccountingDate() {
        return this.accountingDate;
    }

    public TransferDocument accountingDate(LocalDate accountingDate) {
        this.setAccountingDate(accountingDate);
        return this;
    }

    public void setAccountingDate(LocalDate accountingDate) {
        this.accountingDate = accountingDate;
    }

    public LocalDate getReversalDate() {
        return this.reversalDate;
    }

    public TransferDocument reversalDate(LocalDate reversalDate) {
        this.setReversalDate(reversalDate);
        return this;
    }

    public void setReversalDate(LocalDate reversalDate) {
        this.reversalDate = reversalDate;
    }

    public Set<TransferDocumentItem> getTransferDocumentItems() {
        return this.transferDocumentItems;
    }

    public void setTransferDocumentItems(Set<TransferDocumentItem> transferDocumentItems) {
        if (this.transferDocumentItems != null) {
            this.transferDocumentItems.forEach(i -> i.setTransferDocument(null));
        }
        if (transferDocumentItems != null) {
            transferDocumentItems.forEach(i -> i.setTransferDocument(this));
        }
        this.transferDocumentItems = transferDocumentItems;
    }

    public TransferDocument transferDocumentItems(Set<TransferDocumentItem> transferDocumentItems) {
        this.setTransferDocumentItems(transferDocumentItems);
        return this;
    }

    public TransferDocument addTransferDocumentItem(TransferDocumentItem transferDocumentItem) {
        this.transferDocumentItems.add(transferDocumentItem);
        transferDocumentItem.setTransferDocument(this);
        return this;
    }

    public TransferDocument removeTransferDocumentItem(TransferDocumentItem transferDocumentItem) {
        this.transferDocumentItems.remove(transferDocumentItem);
        transferDocumentItem.setTransferDocument(null);
        return this;
    }

    public BusinessYear getBusinessYear() {
        return this.businessYear;
    }

    public void setBusinessYear(BusinessYear businessYear) {
        this.businessYear = businessYear;
    }

    public TransferDocument businessYear(BusinessYear businessYear) {
        this.setBusinessYear(businessYear);
        return this;
    }

    public Storage getReceivingStorage() {
        return this.receivingStorage;
    }

    public void setReceivingStorage(Storage storage) {
        this.receivingStorage = storage;
    }

    public TransferDocument receivingStorage(Storage storage) {
        this.setReceivingStorage(storage);
        return this;
    }

    public Storage getDispatchingStorage() {
        return this.dispatchingStorage;
    }

    public void setDispatchingStorage(Storage storage) {
        this.dispatchingStorage = storage;
    }

    public TransferDocument dispatchingStorage(Storage storage) {
        this.setDispatchingStorage(storage);
        return this;
    }

    public BusinessPartner getBusinessPartner() {
        return this.businessPartner;
    }

    public void setBusinessPartner(BusinessPartner businessPartner) {
        this.businessPartner = businessPartner;
    }

    public TransferDocument businessPartner(BusinessPartner businessPartner) {
        this.setBusinessPartner(businessPartner);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransferDocument)) {
            return false;
        }
        return id != null && id.equals(((TransferDocument) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferDocument{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", transferDate='" + getTransferDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", accountingDate='" + getAccountingDate() + "'" +
            ", reversalDate='" + getReversalDate() + "'" +
            "}";
    }
}
