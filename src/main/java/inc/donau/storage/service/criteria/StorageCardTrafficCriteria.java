package inc.donau.storage.service.criteria;

import inc.donau.storage.domain.enumeration.StorageCardTrafficDirection;
import inc.donau.storage.domain.enumeration.StorageCardTrafficType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.StorageCardTraffic} entity. This class is used
 * in {@link inc.donau.storage.web.rest.StorageCardTrafficResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /storage-card-traffics?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class StorageCardTrafficCriteria implements Serializable, Criteria {

    /**
     * Class for filtering StorageCardTrafficType
     */
    public static class StorageCardTrafficTypeFilter extends Filter<StorageCardTrafficType> {

        public StorageCardTrafficTypeFilter() {}

        public StorageCardTrafficTypeFilter(StorageCardTrafficTypeFilter filter) {
            super(filter);
        }

        @Override
        public StorageCardTrafficTypeFilter copy() {
            return new StorageCardTrafficTypeFilter(this);
        }
    }

    /**
     * Class for filtering StorageCardTrafficDirection
     */
    public static class StorageCardTrafficDirectionFilter extends Filter<StorageCardTrafficDirection> {

        public StorageCardTrafficDirectionFilter() {}

        public StorageCardTrafficDirectionFilter(StorageCardTrafficDirectionFilter filter) {
            super(filter);
        }

        @Override
        public StorageCardTrafficDirectionFilter copy() {
            return new StorageCardTrafficDirectionFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StorageCardTrafficTypeFilter type;

    private StorageCardTrafficDirectionFilter direction;

    private FloatFilter amount;

    private FloatFilter price;

    private FloatFilter trafficValue;

    private StringFilter document;

    private LocalDateFilter date;

    private StringFilter storageCardId;

    private Boolean distinct;

    public StorageCardTrafficCriteria() {}

    public StorageCardTrafficCriteria(StorageCardTrafficCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.direction = other.direction == null ? null : other.direction.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.trafficValue = other.trafficValue == null ? null : other.trafficValue.copy();
        this.document = other.document == null ? null : other.document.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.storageCardId = other.storageCardId == null ? null : other.storageCardId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public StorageCardTrafficCriteria copy() {
        return new StorageCardTrafficCriteria(this);
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

    public StorageCardTrafficTypeFilter getType() {
        return type;
    }

    public StorageCardTrafficTypeFilter type() {
        if (type == null) {
            type = new StorageCardTrafficTypeFilter();
        }
        return type;
    }

    public void setType(StorageCardTrafficTypeFilter type) {
        this.type = type;
    }

    public StorageCardTrafficDirectionFilter getDirection() {
        return direction;
    }

    public StorageCardTrafficDirectionFilter direction() {
        if (direction == null) {
            direction = new StorageCardTrafficDirectionFilter();
        }
        return direction;
    }

    public void setDirection(StorageCardTrafficDirectionFilter direction) {
        this.direction = direction;
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

    public FloatFilter getTrafficValue() {
        return trafficValue;
    }

    public FloatFilter trafficValue() {
        if (trafficValue == null) {
            trafficValue = new FloatFilter();
        }
        return trafficValue;
    }

    public void setTrafficValue(FloatFilter trafficValue) {
        this.trafficValue = trafficValue;
    }

    public StringFilter getDocument() {
        return document;
    }

    public StringFilter document() {
        if (document == null) {
            document = new StringFilter();
        }
        return document;
    }

    public void setDocument(StringFilter document) {
        this.document = document;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public LocalDateFilter date() {
        if (date == null) {
            date = new LocalDateFilter();
        }
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
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
        final StorageCardTrafficCriteria that = (StorageCardTrafficCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(direction, that.direction) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(price, that.price) &&
            Objects.equals(trafficValue, that.trafficValue) &&
            Objects.equals(document, that.document) &&
            Objects.equals(date, that.date) &&
            Objects.equals(storageCardId, that.storageCardId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, direction, amount, price, trafficValue, document, date, storageCardId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StorageCardTrafficCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (direction != null ? "direction=" + direction + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (trafficValue != null ? "trafficValue=" + trafficValue + ", " : "") +
            (document != null ? "document=" + document + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (storageCardId != null ? "storageCardId=" + storageCardId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
