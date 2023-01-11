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

    private Boolean distinct;

    public BusinessYearCriteria() {}

    public BusinessYearCriteria(BusinessYearCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.yearCode = other.yearCode == null ? null : other.yearCode.copy();
        this.completed = other.completed == null ? null : other.completed.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
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
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, yearCode, completed, companyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessYearCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (yearCode != null ? "yearCode=" + yearCode + ", " : "") +
            (completed != null ? "completed=" + completed + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
