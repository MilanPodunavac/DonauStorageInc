package inc.donau.storage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link inc.donau.storage.domain.TransferDocumentItem} entity.
 */
@Schema(description = "sr: Stavka prometnog dokumenta")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransferDocumentItemDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private Float amount;

    @NotNull
    @DecimalMin(value = "0")
    private Float price;

    /**
     * amount x price
     */
    @DecimalMin(value = "0")
    @Schema(description = "amount x price")
    private Float value;

    private TransferDocumentDTO transferDocument;

    private ResourceDTO resource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public TransferDocumentDTO getTransferDocument() {
        return transferDocument;
    }

    public void setTransferDocument(TransferDocumentDTO transferDocument) {
        this.transferDocument = transferDocument;
    }

    public ResourceDTO getResource() {
        return resource;
    }

    public void setResource(ResourceDTO resource) {
        this.resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransferDocumentItemDTO)) {
            return false;
        }

        TransferDocumentItemDTO transferDocumentItemDTO = (TransferDocumentItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, transferDocumentItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferDocumentItemDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", price=" + getPrice() +
            ", value=" + getValue() +
            ", transferDocument=" + getTransferDocument() +
            ", resource=" + getResource() +
            "}";
    }
}
