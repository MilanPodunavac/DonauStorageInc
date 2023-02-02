package inc.donau.storage.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.CensusItem} entity. This class is used
 * in {@link inc.donau.storage.web.rest.CensusItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /census-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CensusItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private FloatFilter amount;

    private LongFilter censusDocumentId;

    private LongFilter resourceId;

    private Boolean distinct;

    public CensusItemCriteria() {}

    public CensusItemCriteria(CensusItemCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.amount = other.amount == null ? null : other.amount.copy();
        this.censusDocumentId = other.censusDocumentId == null ? null : other.censusDocumentId.copy();
        this.resourceId = other.resourceId == null ? null : other.resourceId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CensusItemCriteria copy() {
        return new CensusItemCriteria(this);
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
        final CensusItemCriteria that = (CensusItemCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(censusDocumentId, that.censusDocumentId) &&
            Objects.equals(resourceId, that.resourceId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, censusDocumentId, resourceId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CensusItemCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (amount != null ? "amount=" + amount + ", " : "") +
            (censusDocumentId != null ? "censusDocumentId=" + censusDocumentId + ", " : "") +
            (resourceId != null ? "resourceId=" + resourceId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
