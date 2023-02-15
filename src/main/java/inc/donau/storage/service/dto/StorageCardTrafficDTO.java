package inc.donau.storage.service.dto;

import static java.lang.Math.abs;

import inc.donau.storage.domain.enumeration.StorageCardTrafficDirection;
import inc.donau.storage.domain.enumeration.StorageCardTrafficType;
import inc.donau.storage.domain.enumeration.TransferDocumentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link inc.donau.storage.domain.StorageCardTraffic} entity.
 */
@Schema(description = "sr: Promet magacinske kartice")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StorageCardTrafficDTO implements Serializable {

    private Long id;

    @NotNull
    private StorageCardTrafficType type;

    @NotNull
    private StorageCardTrafficDirection direction;

    @NotNull
    private Float amount;

    @NotNull
    @DecimalMin(value = "0")
    private Float price;

    /**
     * amount x price
     */
    @NotNull
    @Schema(description = "amount x price", required = true)
    private Float trafficValue;

    private String document;

    private LocalDate date;

    private StorageCardDTO storageCard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StorageCardTrafficType getType() {
        return type;
    }

    public void setType(StorageCardTrafficType type) {
        this.type = type;
    }

    public StorageCardTrafficDirection getDirection() {
        return direction;
    }

    public void setDirection(StorageCardTrafficDirection direction) {
        this.direction = direction;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getTrafficValue() {
        return trafficValue;
    }

    public void setTrafficValue(Float trafficValue) {
        this.trafficValue = trafficValue;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public StorageCardDTO getStorageCard() {
        return storageCard;
    }

    public void setStorageCard(StorageCardDTO storageCard) {
        this.storageCard = storageCard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StorageCardTrafficDTO)) {
            return false;
        }

        StorageCardTrafficDTO storageCardTrafficDTO = (StorageCardTrafficDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, storageCardTrafficDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StorageCardTrafficDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", direction='" + getDirection() + "'" +
            ", amount=" + getAmount() +
            ", price=" + getPrice() +
            ", trafficValue=" + getTrafficValue() +
            ", document='" + getDocument() + "'" +
            ", date='" + getDate() + "'" +
            ", storageCard=" + getStorageCard() +
            "}";
    }

    public StorageCardTrafficDTO() {}

    public StorageCardTrafficDTO(StorageCardDTO newStorageCardDTO, StorageCardDTO storageCardDTO) {
        this.storageCard = newStorageCardDTO;
        this.direction = StorageCardTrafficDirection.IN;
        this.type = StorageCardTrafficType.STARTING_BALANCE;
        this.date = LocalDate.now();
        this.amount = storageCardDTO.getTotalAmount();
        this.price = storageCardDTO.getPrice();
        this.trafficValue = storageCardDTO.getTotalValue();
    }

    public StorageCardTrafficDTO(StorageCardDTO storageCardDTO, CensusDocumentDTO censusDocumentDTO, float leveling) {
        this.storageCard = storageCardDTO;
        this.direction = StorageCardTrafficDirection.IN;
        this.type = StorageCardTrafficType.LEVELING;
        this.date = censusDocumentDTO.getAccountingDate();
        this.amount = 0.0f;
        this.price = 0.0f;
        this.trafficValue = leveling;
        this.document = censusDocumentDTO.getId() + "-" + "CENSUS";
    }

    // Correction = Census item amount - Storage card amount
    public StorageCardTrafficDTO(StorageCardDTO storageCardDTO, CensusItemDTO censusItem, float correction) {
        this.storageCard = storageCardDTO;
        this.direction = correction > 0 ? StorageCardTrafficDirection.IN : StorageCardTrafficDirection.OUT;
        this.type = StorageCardTrafficType.CORRECTION;
        this.date = censusItem.getCensusDocument().getAccountingDate();
        this.amount = abs(correction);
        this.price = storageCardDTO.getPrice();
        this.trafficValue = abs(correction) * storageCardDTO.getPrice();
        this.document = censusItem.getCensusDocument().getId() + "-" + "CENSUS";
    }

    public StorageCardTrafficDTO(
        StorageCardDTO storageCardDTO,
        TransferDocumentDTO transferDocumentDTO,
        TransferDocumentItemDTO transferDocumentItemDTO,
        boolean isReceiving
    ) {
        this.storageCard = storageCardDTO;
        this.direction = isReceiving ? StorageCardTrafficDirection.IN : StorageCardTrafficDirection.OUT;
        this.type =
            transferDocumentDTO.getStatus() == TransferDocumentStatus.ACCOUNTED
                ? StorageCardTrafficType.TRANSFER
                : StorageCardTrafficType.REVERSAL;
        this.date =
            transferDocumentDTO.getStatus() == TransferDocumentStatus.ACCOUNTED
                ? transferDocumentDTO.getAccountingDate()
                : transferDocumentDTO.getReversalDate();
        this.amount =
            transferDocumentDTO.getStatus() == TransferDocumentStatus.ACCOUNTED
                ? transferDocumentItemDTO.getAmount()
                : -transferDocumentItemDTO.getAmount();
        this.price = storageCardDTO.getPrice();
        this.trafficValue =
            transferDocumentDTO.getStatus() == TransferDocumentStatus.ACCOUNTED
                ? transferDocumentItemDTO.getTransferValue()
                : -transferDocumentItemDTO.getTransferValue();
        this.document = transferDocumentDTO.getId() + "-" + transferDocumentDTO.getType().toString();
    }
}
