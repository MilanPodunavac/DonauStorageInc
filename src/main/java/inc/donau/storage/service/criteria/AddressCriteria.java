package inc.donau.storage.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.Address} entity. This class is used
 * in {@link inc.donau.storage.web.rest.AddressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /addresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AddressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter streetName;

    private StringFilter streetCode;

    private StringFilter postalCode;

    private LongFilter cityId;

    private LongFilter employeeId;

    private LongFilter legalEntityId;

    private LongFilter storageId;

    private Boolean distinct;

    public AddressCriteria() {}

    public AddressCriteria(AddressCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.streetName = other.streetName == null ? null : other.streetName.copy();
        this.streetCode = other.streetCode == null ? null : other.streetCode.copy();
        this.postalCode = other.postalCode == null ? null : other.postalCode.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.legalEntityId = other.legalEntityId == null ? null : other.legalEntityId.copy();
        this.storageId = other.storageId == null ? null : other.storageId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public AddressCriteria copy() {
        return new AddressCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getStreetName() {
        return streetName;
    }

    public StringFilter streetName() {
        if (streetName == null) {
            streetName = new StringFilter();
        }
        return streetName;
    }

    public void setStreetName(StringFilter streetName) {
        this.streetName = streetName;
    }

    public StringFilter getStreetCode() {
        return streetCode;
    }

    public StringFilter streetCode() {
        if (streetCode == null) {
            streetCode = new StringFilter();
        }
        return streetCode;
    }

    public void setStreetCode(StringFilter streetCode) {
        this.streetCode = streetCode;
    }

    public StringFilter getPostalCode() {
        return postalCode;
    }

    public StringFilter postalCode() {
        if (postalCode == null) {
            postalCode = new StringFilter();
        }
        return postalCode;
    }

    public void setPostalCode(StringFilter postalCode) {
        this.postalCode = postalCode;
    }

    public LongFilter getCityId() {
        return cityId;
    }

    public LongFilter cityId() {
        if (cityId == null) {
            cityId = new LongFilter();
        }
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
    }

    public LongFilter getEmployeeId() {
        return employeeId;
    }

    public LongFilter employeeId() {
        if (employeeId == null) {
            employeeId = new LongFilter();
        }
        return employeeId;
    }

    public void setEmployeeId(LongFilter employeeId) {
        this.employeeId = employeeId;
    }

    public LongFilter getLegalEntityId() {
        return legalEntityId;
    }

    public LongFilter legalEntityId() {
        if (legalEntityId == null) {
            legalEntityId = new LongFilter();
        }
        return legalEntityId;
    }

    public void setLegalEntityId(LongFilter legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    public LongFilter getStorageId() {
        return storageId;
    }

    public LongFilter storageId() {
        if (storageId == null) {
            storageId = new LongFilter();
        }
        return storageId;
    }

    public void setStorageId(LongFilter storageId) {
        this.storageId = storageId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AddressCriteria that = (AddressCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(streetName, that.streetName) &&
            Objects.equals(streetCode, that.streetCode) &&
            Objects.equals(postalCode, that.postalCode) &&
            Objects.equals(cityId, that.cityId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(legalEntityId, that.legalEntityId) &&
            Objects.equals(storageId, that.storageId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, streetName, streetCode, postalCode, cityId, employeeId, legalEntityId, storageId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AddressCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (streetName != null ? "streetName=" + streetName + ", " : "") +
            (streetCode != null ? "streetCode=" + streetCode + ", " : "") +
            (postalCode != null ? "postalCode=" + postalCode + ", " : "") +
            (cityId != null ? "cityId=" + cityId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (legalEntityId != null ? "legalEntityId=" + legalEntityId + ", " : "") +
            (storageId != null ? "storageId=" + storageId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
