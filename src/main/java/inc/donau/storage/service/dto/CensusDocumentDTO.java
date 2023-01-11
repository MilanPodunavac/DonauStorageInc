package inc.donau.storage.service.dto;

import inc.donau.storage.domain.enumeration.CensusDocumentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link inc.donau.storage.domain.CensusDocument} entity.
 */
@Schema(description = "sr: Popisni dokument")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CensusDocumentDTO implements Serializable {

    private Long id;

    /**
     * sr: Popis
     */
    @NotNull
    @Schema(description = "sr: Popis", required = true)
    private LocalDate censusDate;

    private CensusDocumentStatus status;

    /**
     * sr: Knjizenje
     */
    @Schema(description = "sr: Knjizenje")
    private LocalDate accountingDate;

    private Boolean leveling;

    private BusinessYearDTO businessYear;

    private EmployeeDTO president;

    private EmployeeDTO deputy;

    private EmployeeDTO censusTaker;

    private StorageDTO storage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCensusDate() {
        return censusDate;
    }

    public void setCensusDate(LocalDate censusDate) {
        this.censusDate = censusDate;
    }

    public CensusDocumentStatus getStatus() {
        return status;
    }

    public void setStatus(CensusDocumentStatus status) {
        this.status = status;
    }

    public LocalDate getAccountingDate() {
        return accountingDate;
    }

    public void setAccountingDate(LocalDate accountingDate) {
        this.accountingDate = accountingDate;
    }

    public Boolean getLeveling() {
        return leveling;
    }

    public void setLeveling(Boolean leveling) {
        this.leveling = leveling;
    }

    public BusinessYearDTO getBusinessYear() {
        return businessYear;
    }

    public void setBusinessYear(BusinessYearDTO businessYear) {
        this.businessYear = businessYear;
    }

    public EmployeeDTO getPresident() {
        return president;
    }

    public void setPresident(EmployeeDTO president) {
        this.president = president;
    }

    public EmployeeDTO getDeputy() {
        return deputy;
    }

    public void setDeputy(EmployeeDTO deputy) {
        this.deputy = deputy;
    }

    public EmployeeDTO getCensusTaker() {
        return censusTaker;
    }

    public void setCensusTaker(EmployeeDTO censusTaker) {
        this.censusTaker = censusTaker;
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
        if (!(o instanceof CensusDocumentDTO)) {
            return false;
        }

        CensusDocumentDTO censusDocumentDTO = (CensusDocumentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, censusDocumentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CensusDocumentDTO{" +
            "id=" + getId() +
            ", censusDate='" + getCensusDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", accountingDate='" + getAccountingDate() + "'" +
            ", leveling='" + getLeveling() + "'" +
            ", businessYear=" + getBusinessYear() +
            ", president=" + getPresident() +
            ", deputy=" + getDeputy() +
            ", censusTaker=" + getCensusTaker() +
            ", storage=" + getStorage() +
            "}";
    }
}
