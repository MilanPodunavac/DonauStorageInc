package inc.donau.storage.service.dto;

import inc.donau.storage.domain.enumeration.TransferDocumentStatus;
import inc.donau.storage.domain.enumeration.TransferDocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link inc.donau.storage.domain.TransferDocument} entity.
 */
@Schema(description = "sr: Prometni dokument")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransferDocumentDTO implements Serializable {

    private Long id;

    @NotNull
    private TransferDocumentType type;

    @NotNull
    private LocalDate transferDate;

    @NotNull
    private TransferDocumentStatus status;

    /**
     * sr: Knjizenje
     */
    @Schema(description = "sr: Knjizenje")
    private LocalDate accountingDate;

    /**
     * sr: Storniranje
     */
    @Schema(description = "sr: Storniranje")
    private LocalDate reversalDate;

    private BusinessYearDTO businessYear;

    private StorageDTO receivingStorage;

    private StorageDTO dispatchingStorage;

    private BusinessPartnerDTO businessPartner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TransferDocumentType getType() {
        return type;
    }

    public void setType(TransferDocumentType type) {
        this.type = type;
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    public TransferDocumentStatus getStatus() {
        return status;
    }

    public void setStatus(TransferDocumentStatus status) {
        this.status = status;
    }

    public LocalDate getAccountingDate() {
        return accountingDate;
    }

    public void setAccountingDate(LocalDate accountingDate) {
        this.accountingDate = accountingDate;
    }

    public LocalDate getReversalDate() {
        return reversalDate;
    }

    public void setReversalDate(LocalDate reversalDate) {
        this.reversalDate = reversalDate;
    }

    public BusinessYearDTO getBusinessYear() {
        return businessYear;
    }

    public void setBusinessYear(BusinessYearDTO businessYear) {
        this.businessYear = businessYear;
    }

    public StorageDTO getReceivingStorage() {
        return receivingStorage;
    }

    public void setReceivingStorage(StorageDTO receivingStorage) {
        this.receivingStorage = receivingStorage;
    }

    public StorageDTO getDispatchingStorage() {
        return dispatchingStorage;
    }

    public void setDispatchingStorage(StorageDTO dispatchingStorage) {
        this.dispatchingStorage = dispatchingStorage;
    }

    public BusinessPartnerDTO getBusinessPartner() {
        return businessPartner;
    }

    public void setBusinessPartner(BusinessPartnerDTO businessPartner) {
        this.businessPartner = businessPartner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransferDocumentDTO)) {
            return false;
        }

        TransferDocumentDTO transferDocumentDTO = (TransferDocumentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transferDocumentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferDocumentDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", transferDate='" + getTransferDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", accountingDate='" + getAccountingDate() + "'" +
            ", reversalDate='" + getReversalDate() + "'" +
            ", businessYear=" + getBusinessYear() +
            ", receivingStorage=" + getReceivingStorage() +
            ", dispatchingStorage=" + getDispatchingStorage() +
            ", businessPartner=" + getBusinessPartner() +
            "}";
    }
}
