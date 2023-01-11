package inc.donau.storage.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.StorageCard} entity. This class is used
 * in {@link inc.donau.storage.web.rest.StorageCardResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /storage-cards?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StorageCardCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter id;

    private FloatFilter startingAmount;

    private FloatFilter receivedAmount;

    private FloatFilter dispatchedAmount;

    private FloatFilter totalAmount;

    private FloatFilter startingValue;

    private FloatFilter receivedValue;

    private FloatFilter dispatchedValue;

    private FloatFilter totalValue;

    private FloatFilter price;

    private LongFilter storageCardTrafficId;

    private LongFilter businessYearId;

    private LongFilter resourceId;

    private LongFilter storageId;

    private Boolean distinct;

    public StorageCardCriteria() {}

    public StorageCardCriteria(StorageCardCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.startingAmount = other.startingAmount == null ? null : other.startingAmount.copy();
        this.receivedAmount = other.receivedAmount == null ? null : other.receivedAmount.copy();
        this.dispatchedAmount = other.dispatchedAmount == null ? null : other.dispatchedAmount.copy();
        this.totalAmount = other.totalAmount == null ? null : other.totalAmount.copy();
        this.startingValue = other.startingValue == null ? null : other.startingValue.copy();
        this.receivedValue = other.receivedValue == null ? null : other.receivedValue.copy();
        this.dispatchedValue = other.dispatchedValue == null ? null : other.dispatchedValue.copy();
        this.totalValue = other.totalValue == null ? null : other.totalValue.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.storageCardTrafficId = other.storageCardTrafficId == null ? null : other.storageCardTrafficId.copy();
        this.businessYearId = other.businessYearId == null ? null : other.businessYearId.copy();
        this.resourceId = other.resourceId == null ? null : other.resourceId.copy();
        this.storageId = other.storageId == null ? null : other.storageId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public StorageCardCriteria copy() {
        return new StorageCardCriteria(this);
    }

    public StringFilter getId() {
        return id;
    }

    public StringFilter id() {
        if (id == null) {
            id = new StringFilter();
        }
        return id;
    }

    public void setId(StringFilter id) {
        this.id = id;
    }

    public FloatFilter getStartingAmount() {
        return startingAmount;
    }

    public FloatFilter startingAmount() {
        if (startingAmount == null) {
            startingAmount = new FloatFilter();
        }
        return startingAmount;
    }

    public void setStartingAmount(FloatFilter startingAmount) {
        this.startingAmount = startingAmount;
    }

    public FloatFilter getReceivedAmount() {
        return receivedAmount;
    }

    public FloatFilter receivedAmount() {
        if (receivedAmount == null) {
            receivedAmount = new FloatFilter();
        }
        return receivedAmount;
    }

    public void setReceivedAmount(FloatFilter receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public FloatFilter getDispatchedAmount() {
        return dispatchedAmount;
    }

    public FloatFilter dispatchedAmount() {
        if (dispatchedAmount == null) {
            dispatchedAmount = new FloatFilter();
        }
        return dispatchedAmount;
    }

    public void setDispatchedAmount(FloatFilter dispatchedAmount) {
        this.dispatchedAmount = dispatchedAmount;
    }

    public FloatFilter getTotalAmount() {
        return totalAmount;
    }

    public FloatFilter totalAmount() {
        if (totalAmount == null) {
            totalAmount = new FloatFilter();
        }
        return totalAmount;
    }

    public void setTotalAmount(FloatFilter totalAmount) {
        this.totalAmount = totalAmount;
    }

    public FloatFilter getStartingValue() {
        return startingValue;
    }

    public FloatFilter startingValue() {
        if (startingValue == null) {
            startingValue = new FloatFilter();
        }
        return startingValue;
    }

    public void setStartingValue(FloatFilter startingValue) {
        this.startingValue = startingValue;
    }

    public FloatFilter getReceivedValue() {
        return receivedValue;
    }

    public FloatFilter receivedValue() {
        if (receivedValue == null) {
            receivedValue = new FloatFilter();
        }
        return receivedValue;
    }

    public void setReceivedValue(FloatFilter receivedValue) {
        this.receivedValue = receivedValue;
    }

    public FloatFilter getDispatchedValue() {
        return dispatchedValue;
    }

    public FloatFilter dispatchedValue() {
        if (dispatchedValue == null) {
            dispatchedValue = new FloatFilter();
        }
        return dispatchedValue;
    }

    public void setDispatchedValue(FloatFilter dispatchedValue) {
        this.dispatchedValue = dispatchedValue;
    }

    public FloatFilter getTotalValue() {
        return totalValue;
    }

    public FloatFilter totalValue() {
        if (totalValue == null) {
            totalValue = new FloatFilter();
        }
        return totalValue;
    }

    public void setTotalValue(FloatFilter totalValue) {
        this.totalValue = totalValue;
    }

    public FloatFilter getPrice() {
        return price;
    }

    public FloatFilter price() {
        if (price == null) {
            price = new FloatFilter();
        }
        return price;
    }

    public void setPrice(FloatFilter price) {
        this.price = price;
    }

    public LongFilter getStorageCardTrafficId() {
        return storageCardTrafficId;
    }

    public LongFilter storageCardTrafficId() {
        if (storageCardTrafficId == null) {
            storageCardTrafficId = new LongFilter();
        }
        return storageCardTrafficId;
    }

    public void setStorageCardTrafficId(LongFilter storageCardTrafficId) {
        this.storageCardTrafficId = storageCardTrafficId;
    }

    public LongFilter getBusinessYearId() {
        return businessYearId;
    }

    public LongFilter businessYearId() {
        if (businessYearId == null) {
            businessYearId = new LongFilter();
        }
        return businessYearId;
    }

    public void setBusinessYearId(LongFilter businessYearId) {
        this.businessYearId = businessYearId;
    }

    public LongFilter getResourceId() {
        return resourceId;
    }

    public LongFilter resourceId() {
        if (resourceId == null) {
            resourceId = new LongFilter();
        }
        return resourceId;
    }

    public void setResourceId(LongFilter resourceId) {
        this.resourceId = resourceId;
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
        final StorageCardCriteria that = (StorageCardCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(startingAmount, that.startingAmount) &&
            Objects.equals(receivedAmount, that.receivedAmount) &&
            Objects.equals(dispatchedAmount, that.dispatchedAmount) &&
            Objects.equals(totalAmount, that.totalAmount) &&
            Objects.equals(startingValue, that.startingValue) &&
            Objects.equals(receivedValue, that.receivedValue) &&
            Objects.equals(dispatchedValue, that.dispatchedValue) &&
            Objects.equals(totalValue, that.totalValue) &&
            Objects.equals(price, that.price) &&
            Objects.equals(storageCardTrafficId, that.storageCardTrafficId) &&
            Objects.equals(businessYearId, that.businessYearId) &&
            Objects.equals(resourceId, that.resourceId) &&
            Objects.equals(storageId, that.storageId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            startingAmount,
            receivedAmount,
            dispatchedAmount,
            totalAmount,
            startingValue,
            receivedValue,
            dispatchedValue,
            totalValue,
            price,
            storageCardTrafficId,
            businessYearId,
            resourceId,
            storageId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StorageCardCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (startingAmount != null ? "startingAmount=" + startingAmount + ", " : "") +
            (receivedAmount != null ? "receivedAmount=" + receivedAmount + ", " : "") +
            (dispatchedAmount != null ? "dispatchedAmount=" + dispatchedAmount + ", " : "") +
            (totalAmount != null ? "totalAmount=" + totalAmount + ", " : "") +
            (startingValue != null ? "startingValue=" + startingValue + ", " : "") +
            (receivedValue != null ? "receivedValue=" + receivedValue + ", " : "") +
            (dispatchedValue != null ? "dispatchedValue=" + dispatchedValue + ", " : "") +
            (totalValue != null ? "totalValue=" + totalValue + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (storageCardTrafficId != null ? "storageCardTrafficId=" + storageCardTrafficId + ", " : "") +
            (businessYearId != null ? "businessYearId=" + businessYearId + ", " : "") +
            (resourceId != null ? "resourceId=" + resourceId + ", " : "") +
            (storageId != null ? "storageId=" + storageId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
