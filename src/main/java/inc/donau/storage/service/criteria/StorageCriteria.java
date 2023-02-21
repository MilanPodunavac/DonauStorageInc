package inc.donau.storage.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.Storage} entity. This class is used
 * in {@link inc.donau.storage.web.rest.StorageResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /storages?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StorageCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter code;

    private LongFilter addressId;

    private StringFilter storageCardId;

    private LongFilter receivedId;

    private LongFilter dispatchedId;

    private LongFilter censusDocumentId;

    private LongFilter companyId;

    private Boolean distinct;

    public StorageCriteria() {}

    public StorageCriteria(StorageCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.code = other.code == null ? null : other.code.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.storageCardId = other.storageCardId == null ? null : other.storageCardId.copy();
        this.receivedId = other.receivedId == null ? null : other.receivedId.copy();
        this.dispatchedId = other.dispatchedId == null ? null : other.dispatchedId.copy();
        this.censusDocumentId = other.censusDocumentId == null ? null : other.censusDocumentId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public StorageCriteria copy() {
        return new StorageCriteria(this);
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

    public StringFilter getCode() {
        return code;
    }

    public StringFilter code() {
        if (code == null) {
            code = new StringFilter();
        }
        return code;
    }

    public void setCode(StringFilter code) {
        this.code = code;
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

    public StringFilter getStorageCardId() {
        return storageCardId;
    }

    public StringFilter storageCardId() {
        if (storageCardId == null) {
            storageCardId = new StringFilter();
        }
        return storageCardId;
    }

    public void setStorageCardId(StringFilter storageCardId) {
        this.storageCardId = storageCardId;
    }

    public LongFilter getReceivedId() {
        return receivedId;
    }

    public LongFilter receivedId() {
        if (receivedId == null) {
            receivedId = new LongFilter();
        }
        return receivedId;
    }

    public void setReceivedId(LongFilter receivedId) {
        this.receivedId = receivedId;
    }

    public LongFilter getDispatchedId() {
        return dispatchedId;
    }

    public LongFilter dispatchedId() {
        if (dispatchedId == null) {
            dispatchedId = new LongFilter();
        }
        return dispatchedId;
    }

    public void setDispatchedId(LongFilter dispatchedId) {
        this.dispatchedId = dispatchedId;
    }

    public LongFilter getCensusDocumentId() {
        return censusDocumentId;
    }

    public LongFilter censusDocumentId() {
        if (censusDocumentId == null) {
            censusDocumentId = new LongFilter();
        }
        return censusDocumentId;
    }

    public void setCensusDocumentId(LongFilter censusDocumentId) {
        this.censusDocumentId = censusDocumentId;
    }

    public LongFilter getCompanyId() {
        return companyId;
    }

    public LongFilter companyId() {
        if (companyId == null) {
            companyId = new LongFilter();
        }
        return companyId;
    }

    public void setCompanyId(LongFilter companyId) {
        this.companyId = companyId;
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
        final StorageCriteria that = (StorageCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(code, that.code) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(storageCardId, that.storageCardId) &&
            Objects.equals(receivedId, that.receivedId) &&
            Objects.equals(dispatchedId, that.dispatchedId) &&
            Objects.equals(censusDocumentId, that.censusDocumentId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, addressId, storageCardId, receivedId, dispatchedId, censusDocumentId, companyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StorageCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (code != null ? "code=" + code + ", " : "") +
            (addressId != null ? "addressId=" + addressId + ", " : "") +
            (storageCardId != null ? "storageCardId=" + storageCardId + ", " : "") +
            (receivedId != null ? "receivedId=" + receivedId + ", " : "") +
            (dispatchedId != null ? "dispatchedId=" + dispatchedId + ", " : "") +
            (censusDocumentId != null ? "censusDocumentId=" + censusDocumentId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
