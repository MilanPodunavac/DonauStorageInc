package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import inc.donau.storage.domain.enumeration.StorageCardTrafficDirection;
import inc.donau.storage.domain.enumeration.StorageCardTrafficType;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * sr: Promet magacinske kartice
 */
@Entity
@Table(name = "storage_card_traffic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StorageCardTraffic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private StorageCardTrafficType type;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "direction", nullable = false)
    private StorageCardTrafficDirection direction;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Float amount;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "price", nullable = false)
    private Float price;

    /**
     * amount x price
     */
    @NotNull
    @Column(name = "traffic_value", nullable = false)
    private Float trafficValue;

    @Column(name = "document")
    private String document;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "traffic", "businessYear", "resource", "storage" }, allowSetters = true)
    private StorageCard storageCard;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public StorageCardTraffic id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StorageCardTrafficType getType() {
        return this.type;
    }

    public StorageCardTraffic type(StorageCardTrafficType type) {
        this.setType(type);
        return this;
    }

    public void setType(StorageCardTrafficType type) {
        this.type = type;
    }

    public StorageCardTrafficDirection getDirection() {
        return this.direction;
    }

    public StorageCardTraffic direction(StorageCardTrafficDirection direction) {
        this.setDirection(direction);
        return this;
    }

    public void setDirection(StorageCardTrafficDirection direction) {
        this.direction = direction;
    }

    public Float getAmount() {
        return this.amount;
    }

    public StorageCardTraffic amount(Float amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getPrice() {
        return this.price;
    }

    public StorageCardTraffic price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTrafficValue() {
        return this.trafficValue;
    }

    public StorageCardTraffic trafficValue(Float trafficValue) {
        this.setTrafficValue(trafficValue);
        return this;
    }

    public void setTrafficValue(Float trafficValue) {
        this.trafficValue = trafficValue;
    }

    public String getDocument() {
        return this.document;
    }

    public StorageCardTraffic document(String document) {
        this.setDocument(document);
        return this;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public StorageCardTraffic date(LocalDate date) {
        this.setDate(date);
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public StorageCard getStorageCard() {
        return this.storageCard;
    }

    public void setStorageCard(StorageCard storageCard) {
        this.storageCard = storageCard;
    }

    public StorageCardTraffic storageCard(StorageCard storageCard) {
        this.setStorageCard(storageCard);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StorageCardTraffic)) {
            return false;
        }
        return id != null && id.equals(((StorageCardTraffic) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StorageCardTraffic{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", direction='" + getDirection() + "'" +
            ", amount=" + getAmount() +
            ", price=" + getPrice() +
            ", trafficValue=" + getTrafficValue() +
            ", document='" + getDocument() + "'" +
            ", date='" + getDate() + "'" +
            "}";
    }
}
