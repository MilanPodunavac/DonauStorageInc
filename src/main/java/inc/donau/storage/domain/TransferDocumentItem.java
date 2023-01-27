package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * sr: Stavka prometnog dokumenta
 */
@Entity
@Table(name = "transfer_document_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransferDocumentItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "amount", nullable = false)
    private Float amount;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", nullable = false)
    private Float price;

    /**
     * amount x price
     */
    @DecimalMin(value = "0")
    @Column(name = "transfer_value")
    private Float transferValue;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "transferDocumentItems", "businessYear", "receivingStorage", "dispatchingStorage", "businessPartner" },
        allowSetters = true
    )
    private TransferDocument transferDocument;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "censusItems", "transferDocumentItems", "unit", "company" }, allowSetters = true)
    private Resource resource;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TransferDocumentItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return this.amount;
    }

    public TransferDocumentItem amount(Float amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getPrice() {
        return this.price;
    }

    public TransferDocumentItem price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTransferValue() {
        return this.transferValue;
    }

    public TransferDocumentItem transferValue(Float transferValue) {
        this.setTransferValue(transferValue);
        return this;
    }

    public void setTransferValue(Float transferValue) {
        this.transferValue = transferValue;
    }

    public TransferDocument getTransferDocument() {
        return this.transferDocument;
    }

    public void setTransferDocument(TransferDocument transferDocument) {
        this.transferDocument = transferDocument;
    }

    public TransferDocumentItem transferDocument(TransferDocument transferDocument) {
        this.setTransferDocument(transferDocument);
        return this;
    }

    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public TransferDocumentItem resource(Resource resource) {
        this.setResource(resource);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransferDocumentItem)) {
            return false;
        }
        return id != null && id.equals(((TransferDocumentItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferDocumentItem{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", price=" + getPrice() +
            ", transferValue=" + getTransferValue() +
            "}";
    }
}
