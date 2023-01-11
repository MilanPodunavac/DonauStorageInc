package inc.donau.storage.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link inc.donau.storage.domain.MeasurementUnit} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MeasurementUnitDTO implements Serializable {

    private Long id;

    private String name;

    private String abbreviation;

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

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeasurementUnitDTO)) {
            return false;
        }

        MeasurementUnitDTO measurementUnitDTO = (MeasurementUnitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, measurementUnitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MeasurementUnitDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", abbreviation='" + getAbbreviation() + "'" +
            "}";
    }
}
