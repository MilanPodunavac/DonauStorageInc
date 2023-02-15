package inc.donau.storage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link inc.donau.storage.domain.CensusItem} entity.
 */
@Schema(description = "sr: Stavka popisa")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CensusItemDTO implements Serializable {

    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    private Float amount;

    private CensusDocumentDTO censusDocument;

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

    public CensusDocumentDTO getCensusDocument() {
        return censusDocument;
    }

    public void setCensusDocument(CensusDocumentDTO censusDocument) {
        this.censusDocument = censusDocument;
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
        if (!(o instanceof CensusItemDTO)) {
            return false;
        }

        CensusItemDTO censusItemDTO = (CensusItemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, censusItemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CensusItemDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", censusDocument=" + getCensusDocument() +
            ", resource=" + getResource() +
            "}";
    }

    public CensusItemDTO() {}

    public CensusItemDTO(ResourceDTO resourceDTO, CensusDocumentDTO censusDocumentDTO) {
        this.amount = 0.0f;
        this.censusDocument = censusDocumentDTO;
        this.resource = resourceDTO;
    }
}
