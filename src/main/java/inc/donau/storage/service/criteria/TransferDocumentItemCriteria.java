package inc.donau.storage.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.TransferDocumentItem} entity. This class is used
 * in {@link inc.donau.storage.web.rest.TransferDocumentItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transfer-document-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransferDocumentItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter amount;

    private FloatFilter price;

    private FloatFilter transferValue;

    private LongFilter transferDocumentId;

    private LongFilter resourceId;

    private Boolean distinct;

    public TransferDocumentItemCriteria() {}

    public TransferDocumentItemCriteria(TransferDocumentItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.transferValue = other.transferValue == null ? null : other.transferValue.copy();
        this.transferDocumentId = other.transferDocumentId == null ? null : other.transferDocumentId.copy();
        this.resourceId = other.resourceId == null ? null : other.resourceId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TransferDocumentItemCriteria copy() {
        return new TransferDocumentItemCriteria(this);
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

    public FloatFilter getAmount() {
        return amount;
    }

    public FloatFilter amount() {
        if (amount == null) {
            amount = new FloatFilter();
        }
        return amount;
    }

    public void setAmount(FloatFilter amount) {
        this.amount = amount;
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

    public FloatFilter getTransferValue() {
        return transferValue;
    }

    public FloatFilter transferValue() {
        if (transferValue == null) {
            transferValue = new FloatFilter();
        }
        return transferValue;
    }

    public void setTransferValue(FloatFilter transferValue) {
        this.transferValue = transferValue;
    }

    public LongFilter getTransferDocumentId() {
        return transferDocumentId;
    }

    public LongFilter transferDocumentId() {
        if (transferDocumentId == null) {
            transferDocumentId = new LongFilter();
        }
        return transferDocumentId;
    }

    public void setTransferDocumentId(LongFilter transferDocumentId) {
        this.transferDocumentId = transferDocumentId;
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
        final TransferDocumentItemCriteria that = (TransferDocumentItemCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(price, that.price) &&
            Objects.equals(transferValue, that.transferValue) &&
            Objects.equals(transferDocumentId, that.transferDocumentId) &&
            Objects.equals(resourceId, that.resourceId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, price, transferValue, transferDocumentId, resourceId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferDocumentItemCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (transferValue != null ? "transferValue=" + transferValue + ", " : "") +
            (transferDocumentId != null ? "transferDocumentId=" + transferDocumentId + ", " : "") +
            (resourceId != null ? "resourceId=" + resourceId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
