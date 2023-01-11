package inc.donau.storage.service.dto;

import inc.donau.storage.domain.enumeration.ResourceType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link inc.donau.storage.domain.Resource} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResourceDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private ResourceType type;

    private MeasurementUnitDTO unit;

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

    public ResourceType getType() {
        return type;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public MeasurementUnitDTO getUnit() {
        return unit;
    }

    public void setUnit(MeasurementUnitDTO unit) {
        this.unit = unit;
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
        if (!(o instanceof ResourceDTO)) {
            return false;
        }

        ResourceDTO resourceDTO = (ResourceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, resourceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", unit=" + getUnit() +
            ", company=" + getCompany() +
            "}";
    }
}
