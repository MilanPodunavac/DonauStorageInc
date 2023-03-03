package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;

/**
 * sr: Magacinska kartica
 */
@JsonIgnoreProperties(value = { "new" })
@Entity
@Table(name = "storage_card")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StorageCard implements Serializable, Persistable<String> {

    private static final long serialVersionUID = 1L;

    /**
     * Generated based on year, storage and resource
     */
    @Id
    @Column(name = "id")
    private String id;

    /**
     * sr: Pocetno stanje kolicinski
     */
    @NotNull
    @Column(name = "starting_amount", nullable = false)
    private Float startingAmount;

    /**
     * sr: Promet ulaza kolicinski
     */
    @NotNull
    @Column(name = "received_amount", nullable = false)
    private Float receivedAmount;

    /**
     * sr: Promet izlaza kolicinski
     */
    @NotNull
    @Column(name = "dispatched_amount", nullable = false)
    private Float dispatchedAmount;

    /**
     * [startingAmount] + [receivedAmount] - [dispatchedAmount]\nsr: Ukupna kolicina
     */
    @Column(name = "total_amount")
    private Float totalAmount;

    /**
     * sr: Pocetno stanje vrednosno
     */
    @NotNull
    @Column(name = "starting_value", nullable = false)
    private Float startingValue;

    /**
     * sr: Promet ulaza vrednosno
     */
    @NotNull
    @Column(name = "received_value", nullable = false)
    private Float receivedValue;

    /**
     * sr: Promet izlaza vrednosno
     */
    @NotNull
    @Column(name = "dispatched_value", nullable = false)
    private Float dispatchedValue;

    /**
     * [startingValue] + [receivedValue] - [dispatchedValue]\nsr: Ukupna vrednost
     */
    @Column(name = "total_value")
    private Float totalValue;

    @NotNull
    @Column(name = "price", nullable = false)
    private Float price;

    @Transient
    private boolean isPersisted;

    /**
     * Prevent deletion
     */
    @OneToMany(mappedBy = "storageCard")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "storageCard" }, allowSetters = true)
    private Set<StorageCardTraffic> traffic = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "company", "censusDocuments", "storageCards", "transfers" }, allowSetters = true)
    private BusinessYear businessYear;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "censusItems", "transferItems", "unit", "company", "storageCards" }, allowSetters = true)
    private Resource resource;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "address", "storageCards", "receiveds", "dispatcheds", "censusDocuments", "company" },
        allowSetters = true
    )
    private Storage storage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public StorageCard id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getStartingAmount() {
        return this.startingAmount;
    }

    public StorageCard startingAmount(Float startingAmount) {
        this.setStartingAmount(startingAmount);
        return this;
    }

    public void setStartingAmount(Float startingAmount) {
        this.startingAmount = startingAmount;
    }

    public Float getReceivedAmount() {
        return this.receivedAmount;
    }

    public StorageCard receivedAmount(Float receivedAmount) {
        this.setReceivedAmount(receivedAmount);
        return this;
    }

    public void setReceivedAmount(Float receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public Float getDispatchedAmount() {
        return this.dispatchedAmount;
    }

    public StorageCard dispatchedAmount(Float dispatchedAmount) {
        this.setDispatchedAmount(dispatchedAmount);
        return this;
    }

    public void setDispatchedAmount(Float dispatchedAmount) {
        this.dispatchedAmount = dispatchedAmount;
    }

    public Float getTotalAmount() {
        return this.totalAmount;
    }

    public StorageCard totalAmount(Float totalAmount) {
        this.setTotalAmount(totalAmount);
        return this;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Float getStartingValue() {
        return this.startingValue;
    }

    public StorageCard startingValue(Float startingValue) {
        this.setStartingValue(startingValue);
        return this;
    }

    public void setStartingValue(Float startingValue) {
        this.startingValue = startingValue;
    }

    public Float getReceivedValue() {
        return this.receivedValue;
    }

    public StorageCard receivedValue(Float receivedValue) {
        this.setReceivedValue(receivedValue);
        return this;
    }

    public void setReceivedValue(Float receivedValue) {
        this.receivedValue = receivedValue;
    }

    public Float getDispatchedValue() {
        return this.dispatchedValue;
    }

    public StorageCard dispatchedValue(Float dispatchedValue) {
        this.setDispatchedValue(dispatchedValue);
        return this;
    }

    public void setDispatchedValue(Float dispatchedValue) {
        this.dispatchedValue = dispatchedValue;
    }

    public Float getTotalValue() {
        return this.totalValue;
    }

    public StorageCard totalValue(Float totalValue) {
        this.setTotalValue(totalValue);
        return this;
    }

    public void setTotalValue(Float totalValue) {
        this.totalValue = totalValue;
    }

    public Float getPrice() {
        return this.price;
    }

    public StorageCard price(Float price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @Transient
    @Override
    public boolean isNew() {
        return !this.isPersisted;
    }

    public StorageCard setIsPersisted() {
        this.isPersisted = true;
        return this;
    }

    @PostLoad
    @PostPersist
    public void updateEntityState() {
        this.setIsPersisted();
    }

    public Set<StorageCardTraffic> getTraffic() {
        return this.traffic;
    }

    public void setTraffic(Set<StorageCardTraffic> storageCardTraffics) {
        if (this.traffic != null) {
            this.traffic.forEach(i -> i.setStorageCard(null));
        }
        if (storageCardTraffics != null) {
            storageCardTraffics.forEach(i -> i.setStorageCard(this));
        }
        this.traffic = storageCardTraffics;
    }

    public StorageCard traffic(Set<StorageCardTraffic> storageCardTraffics) {
        this.setTraffic(storageCardTraffics);
        return this;
    }

    public StorageCard addTraffic(StorageCardTraffic storageCardTraffic) {
        this.traffic.add(storageCardTraffic);
        storageCardTraffic.setStorageCard(this);
        return this;
    }

    public StorageCard removeTraffic(StorageCardTraffic storageCardTraffic) {
        this.traffic.remove(storageCardTraffic);
        storageCardTraffic.setStorageCard(null);
        return this;
    }

    public BusinessYear getBusinessYear() {
        return this.businessYear;
    }

    public void setBusinessYear(BusinessYear businessYear) {
        this.businessYear = businessYear;
    }

    public StorageCard businessYear(BusinessYear businessYear) {
        this.setBusinessYear(businessYear);
        return this;
    }

    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public StorageCard resource(Resource resource) {
        this.setResource(resource);
        return this;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public StorageCard storage(Storage storage) {
        this.setStorage(storage);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StorageCard)) {
            return false;
        }
        return id != null && id.equals(((StorageCard) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StorageCard{" +
            "id=" + getId() +
            ", startingAmount=" + getStartingAmount() +
            ", receivedAmount=" + getReceivedAmount() +
            ", dispatchedAmount=" + getDispatchedAmount() +
            ", totalAmount=" + getTotalAmount() +
            ", startingValue=" + getStartingValue() +
            ", receivedValue=" + getReceivedValue() +
            ", dispatchedValue=" + getDispatchedValue() +
            ", totalValue=" + getTotalValue() +
            ", price=" + getPrice() +
            "}";
    }
}
