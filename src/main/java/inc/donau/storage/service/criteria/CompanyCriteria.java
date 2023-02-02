package inc.donau.storage.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.Company} entity. This class is used
 * in {@link inc.donau.storage.web.rest.CompanyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /companies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter legalEntityInfoId;

    private LongFilter resourceId;

    private LongFilter businessPartnerId;

    private LongFilter businessYearId;

    private LongFilter employeeId;

    private Boolean distinct;

    public CompanyCriteria() {}

    public CompanyCriteria(CompanyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.legalEntityInfoId = other.legalEntityInfoId == null ? null : other.legalEntityInfoId.copy();
        this.resourceId = other.resourceId == null ? null : other.resourceId.copy();
        this.businessPartnerId = other.businessPartnerId == null ? null : other.businessPartnerId.copy();
        this.businessYearId = other.businessYearId == null ? null : other.businessYearId.copy();
        this.employeeId = other.employeeId == null ? null : other.employeeId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CompanyCriteria copy() {
        return new CompanyCriteria(this);
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

    public LongFilter getLegalEntityInfoId() {
        return legalEntityInfoId;
    }

    public LongFilter legalEntityInfoId() {
        if (legalEntityInfoId == null) {
            legalEntityInfoId = new LongFilter();
        }
        return legalEntityInfoId;
    }

    public void setLegalEntityInfoId(LongFilter legalEntityInfoId) {
        this.legalEntityInfoId = legalEntityInfoId;
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

    public LongFilter getBusinessPartnerId() {
        return businessPartnerId;
    }

    public LongFilter businessPartnerId() {
        if (businessPartnerId == null) {
            businessPartnerId = new LongFilter();
        }
        return businessPartnerId;
    }

    public void setBusinessPartnerId(LongFilter businessPartnerId) {
        this.businessPartnerId = businessPartnerId;
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
        final CompanyCriteria that = (CompanyCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(legalEntityInfoId, that.legalEntityInfoId) &&
            Objects.equals(resourceId, that.resourceId) &&
            Objects.equals(businessPartnerId, that.businessPartnerId) &&
            Objects.equals(businessYearId, that.businessYearId) &&
            Objects.equals(employeeId, that.employeeId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, legalEntityInfoId, resourceId, businessPartnerId, businessYearId, employeeId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (legalEntityInfoId != null ? "legalEntityInfoId=" + legalEntityInfoId + ", " : "") +
            (resourceId != null ? "resourceId=" + resourceId + ", " : "") +
            (businessPartnerId != null ? "businessPartnerId=" + businessPartnerId + ", " : "") +
            (businessYearId != null ? "businessYearId=" + businessYearId + ", " : "") +
            (employeeId != null ? "employeeId=" + employeeId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
