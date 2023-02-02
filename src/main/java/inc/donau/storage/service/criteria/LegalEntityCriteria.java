package inc.donau.storage.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.LegalEntity} entity. This class is used
 * in {@link inc.donau.storage.web.rest.LegalEntityResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /legal-entities?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LegalEntityCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter taxIdentificationNumber;

    private StringFilter identificationNumber;

    private LongFilter contactInfoId;

    private LongFilter addressId;

    private Boolean distinct;

    public LegalEntityCriteria() {}

    public LegalEntityCriteria(LegalEntityCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.taxIdentificationNumber = other.taxIdentificationNumber == null ? null : other.taxIdentificationNumber.copy();
        this.identificationNumber = other.identificationNumber == null ? null : other.identificationNumber.copy();
        this.contactInfoId = other.contactInfoId == null ? null : other.contactInfoId.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public LegalEntityCriteria copy() {
        return new LegalEntityCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public StringFilter taxIdentificationNumber() {
        if (taxIdentificationNumber == null) {
            taxIdentificationNumber = new StringFilter();
        }
        return taxIdentificationNumber;
    }

    public void setTaxIdentificationNumber(StringFilter taxIdentificationNumber) {
        this.taxIdentificationNumber = taxIdentificationNumber;
    }

    public StringFilter getIdentificationNumber() {
        return identificationNumber;
    }

    public StringFilter identificationNumber() {
        if (identificationNumber == null) {
            identificationNumber = new StringFilter();
        }
        return identificationNumber;
    }

    public void setIdentificationNumber(StringFilter identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public LongFilter getContactInfoId() {
        return contactInfoId;
    }

    public LongFilter contactInfoId() {
        if (contactInfoId == null) {
            contactInfoId = new LongFilter();
        }
        return contactInfoId;
    }

    public void setContactInfoId(LongFilter contactInfoId) {
        this.contactInfoId = contactInfoId;
    }

    public LongFilter getAddressId() {
        return addressId;
    }

    public LongFilter addressId() {
        if (addressId == null) {
            addressId = new LongFilter();
        }
        return addressId;
    }

    public void setAddressId(LongFilter addressId) {
        this.addressId = addressId;
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
        final LegalEntityCriteria that = (LegalEntityCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(taxIdentificationNumber, that.taxIdentificationNumber) &&
            Objects.equals(identificationNumber, that.identificationNumber) &&
            Objects.equals(contactInfoId, that.contactInfoId) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, taxIdentificationNumber, identificationNumber, contactInfoId, addressId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LegalEntityCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (taxIdentificationNumber != null ? "taxIdentificationNumber=" + taxIdentificationNumber + ", " : "") +
            (identificationNumber != null ? "identificationNumber=" + identificationNumber + ", " : "") +
            (contactInfoId != null ? "contactInfoId=" + contactInfoId + ", " : "") +
            (addressId != null ? "addressId=" + addressId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
