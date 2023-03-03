package inc.donau.storage.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.BusinessYear} entity. This class is used
 * in {@link inc.donau.storage.web.rest.BusinessYearResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /business-years?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessYearCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter yearCode;

    private BooleanFilter completed;

    private LongFilter companyId;

    private LongFilter censusDocumentsId;

    private StringFilter storageCardsId;

    private LongFilter transfersId;

    private Boolean distinct;

    public BusinessYearCriteria() {}

    public BusinessYearCriteria(BusinessYearCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.yearCode = other.yearCode == null ? null : other.yearCode.copy();
        this.completed = other.completed == null ? null : other.completed.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.censusDocumentsId = other.censusDocumentsId == null ? null : other.censusDocumentsId.copy();
        this.storageCardsId = other.storageCardsId == null ? null : other.storageCardsId.copy();
        this.transfersId = other.transfersId == null ? null : other.transfersId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BusinessYearCriteria copy() {
        return new BusinessYearCriteria(this);
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

    public StringFilter getYearCode() {
        return yearCode;
    }

    public StringFilter yearCode() {
        if (yearCode == null) {
            yearCode = new StringFilter();
        }
        return yearCode;
    }

    public void setYearCode(StringFilter yearCode) {
        this.yearCode = yearCode;
    }

    public BooleanFilter getCompleted() {
        return completed;
    }

    public BooleanFilter completed() {
        if (completed == null) {
            completed = new BooleanFilter();
        }
        return completed;
    }

    public void setCompleted(BooleanFilter completed) {
        this.completed = completed;
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

    public LongFilter getCensusDocumentsId() {
        return censusDocumentsId;
    }

    public LongFilter censusDocumentsId() {
        if (censusDocumentsId == null) {
            censusDocumentsId = new LongFilter();
        }
        return censusDocumentsId;
    }

    public void setCensusDocumentsId(LongFilter censusDocumentsId) {
        this.censusDocumentsId = censusDocumentsId;
    }

    public StringFilter getStorageCardsId() {
        return storageCardsId;
    }

    public StringFilter storageCardsId() {
        if (storageCardsId == null) {
            storageCardsId = new StringFilter();
        }
        return storageCardsId;
    }

    public void setStorageCardsId(StringFilter storageCardsId) {
        this.storageCardsId = storageCardsId;
    }

    public LongFilter getTransfersId() {
        return transfersId;
    }

    public LongFilter transfersId() {
        if (transfersId == null) {
            transfersId = new LongFilter();
        }
        return transfersId;
    }

    public void setTransfersId(LongFilter transfersId) {
        this.transfersId = transfersId;
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
        final BusinessYearCriteria that = (BusinessYearCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(yearCode, that.yearCode) &&
            Objects.equals(completed, that.completed) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(censusDocumentsId, that.censusDocumentsId) &&
            Objects.equals(storageCardsId, that.storageCardsId) &&
            Objects.equals(transfersId, that.transfersId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, yearCode, completed, companyId, censusDocumentsId, storageCardsId, transfersId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessYearCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (yearCode != null ? "yearCode=" + yearCode + ", " : "") +
            (completed != null ? "completed=" + completed + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (censusDocumentsId != null ? "censusDocumentsId=" + censusDocumentsId + ", " : "") +
            (storageCardsId != null ? "storageCardsId=" + storageCardsId + ", " : "") +
            (transfersId != null ? "transfersId=" + transfersId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
