package inc.donau.storage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link inc.donau.storage.domain.Storage} entity.
 */
@Schema(description = "sr: Magacin")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StorageDTO implements Serializable {

    /**
     * AutoNumber
     */
    @Schema(description = "AutoNumber")
    private Long id;

    private String name;

    private AddressDTO address;

    private CompanyDTO company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StorageDTO)) {
            return false;
        }

        StorageDTO storageDTO = (StorageDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, storageDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StorageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address=" + getAddress() +
            ", company=" + getCompany() +
            "}";
    }
}
