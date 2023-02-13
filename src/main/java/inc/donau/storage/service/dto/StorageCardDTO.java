package inc.donau.storage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link inc.donau.storage.domain.StorageCard} entity.
 */
@Schema(description = "sr: Magacinska kartica")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StorageCardDTO implements Serializable {

    /**
     * Generated based on year, storage and resource
     */
    @Schema(description = "Generated based on year, storage and resource")
    private String id;

    /**
     * sr: Pocetno stanje kolicinski
     */
    @NotNull
    @Schema(description = "sr: Pocetno stanje kolicinski", required = true)
    private Float startingAmount;

    /**
     * sr: Promet ulaza kolicinski
     */
    @NotNull
    @Schema(description = "sr: Promet ulaza kolicinski", required = true)
    private Float receivedAmount;

    /**
     * sr: Promet izlaza kolicinski
     */
    @NotNull
    @Schema(description = "sr: Promet izlaza kolicinski", required = true)
    private Float dispatchedAmount;

    /**
     * [startingAmount] + [receivedAmount] - [dispatchedAmount]\nsr: Ukupna kolicina
     */
    @Schema(description = "[startingAmount] + [receivedAmount] - [dispatchedAmount]\nsr: Ukupna kolicina")
    private Float totalAmount;

    /**
     * sr: Pocetno stanje vrednosno
     */
    @NotNull
    @Schema(description = "sr: Pocetno stanje vrednosno", required = true)
    private Float startingValue;

    /**
     * sr: Promet ulaza vrednosno
     */
    @NotNull
    @Schema(description = "sr: Promet ulaza vrednosno", required = true)
    private Float receivedValue;

    /**
     * sr: Promet izlaza vrednosno
     */
    @NotNull
    @Schema(description = "sr: Promet izlaza vrednosno", required = true)
    private Float dispatchedValue;

    /**
     * [startingValue] + [receivedValue] - [dispatchedValue]\nsr: Ukupna vrednost
     */
    @Schema(description = "[startingValue] + [receivedValue] - [dispatchedValue]\nsr: Ukupna vrednost")
    private Float totalValue;

    @NotNull
    private Float price;

    private BusinessYearDTO businessYear;

    private ResourceDTO resource;

    private StorageDTO storage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getStartingAmount() {
        return startingAmount;
    }

    public void setStartingAmount(Float startingAmount) {
        this.startingAmount = startingAmount;
    }

    public Float getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(Float receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public Float getDispatchedAmount() {
        return dispatchedAmount;
    }

    public void setDispatchedAmount(Float dispatchedAmount) {
        this.dispatchedAmount = dispatchedAmount;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Float getStartingValue() {
        return startingValue;
    }

    public void setStartingValue(Float startingValue) {
        this.startingValue = startingValue;
    }

    public Float getReceivedValue() {
        return receivedValue;
    }

    public void setReceivedValue(Float receivedValue) {
        this.receivedValue = receivedValue;
    }

    public Float getDispatchedValue() {
        return dispatchedValue;
    }

    public void setDispatchedValue(Float dispatchedValue) {
        this.dispatchedValue = dispatchedValue;
    }

    public Float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Float totalValue) {
        this.totalValue = totalValue;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public BusinessYearDTO getBusinessYear() {
        return businessYear;
    }

    public void setBusinessYear(BusinessYearDTO businessYear) {
        this.businessYear = businessYear;
    }

    public ResourceDTO getResource() {
        return resource;
    }

    public void setResource(ResourceDTO resource) {
        this.resource = resource;
    }

    public StorageDTO getStorage() {
        return storage;
    }

    public void setStorage(StorageDTO storage) {
        this.storage = storage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StorageCardDTO)) {
            return false;
        }

        StorageCardDTO storageCardDTO = (StorageCardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, storageCardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StorageCardDTO{" +
            "id='" + getId() + "'" +
            ", startingAmount=" + getStartingAmount() +
            ", receivedAmount=" + getReceivedAmount() +
            ", dispatchedAmount=" + getDispatchedAmount() +
            ", totalAmount=" + getTotalAmount() +
            ", startingValue=" + getStartingValue() +
            ", receivedValue=" + getReceivedValue() +
            ", dispatchedValue=" + getDispatchedValue() +
            ", totalValue=" + getTotalValue() +
            ", price=" + getPrice() +
            ", businessYear=" + getBusinessYear() +
            ", resource=" + getResource() +
            ", storage=" + getStorage() +
            "}";
    }

    public StorageCardDTO() {}

    public StorageCardDTO(BusinessYearDTO businessYearDTO, ResourceDTO resourceDTO, StorageDTO storageDTO) {
        this.businessYear = businessYearDTO;
        this.resource = resourceDTO;
        this.storage = storageDTO;
        this.id = storageDTO.getId() + "-" + resourceDTO.getId() + "-" + businessYearDTO.getId();

        this.startingAmount = 0.0f;
        this.receivedAmount = 0.0f;
        this.dispatchedAmount = 0.0f;
        this.totalAmount = 0.0f;
        this.startingValue = 0.0f;
        this.receivedValue = 0.0f;
        this.dispatchedValue = 0.0f;
        this.totalValue = 0.0f;
        this.price = 0.0f;
    }
}
