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

    private LongFilter resourcesId;

    private LongFilter partnersId;

    private LongFilter businessYearsId;

    private LongFilter employeesId;

    private LongFilter storagesId;

    private Boolean distinct;

    public CompanyCriteria() {}

    public CompanyCriteria(CompanyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.legalEntityInfoId = other.legalEntityInfoId == null ? null : other.legalEntityInfoId.copy();
        this.resourcesId = other.resourcesId == null ? null : other.resourcesId.copy();
        this.partnersId = other.partnersId == null ? null : other.partnersId.copy();
        this.businessYearsId = other.businessYearsId == null ? null : other.businessYearsId.copy();
        this.employeesId = other.employeesId == null ? null : other.employeesId.copy();
        this.storagesId = other.storagesId == null ? null : other.storagesId.copy();
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

    public LongFilter getResourcesId() {
        return resourcesId;
    }

    public LongFilter resourcesId() {
        if (resourcesId == null) {
            resourcesId = new LongFilter();
        }
        return resourcesId;
    }

    public void setResourcesId(LongFilter resourcesId) {
        this.resourcesId = resourcesId;
    }

    public LongFilter getPartnersId() {
        return partnersId;
    }

    public LongFilter partnersId() {
        if (partnersId == null) {
            partnersId = new LongFilter();
        }
        return partnersId;
    }

    public void setPartnersId(LongFilter partnersId) {
        this.partnersId = partnersId;
    }

    public LongFilter getBusinessYearsId() {
        return businessYearsId;
    }

    public LongFilter businessYearsId() {
        if (businessYearsId == null) {
            businessYearsId = new LongFilter();
        }
        return businessYearsId;
    }

    public void setBusinessYearsId(LongFilter businessYearsId) {
        this.businessYearsId = businessYearsId;
    }

    public LongFilter getEmployeesId() {
        return employeesId;
    }

    public LongFilter employeesId() {
        if (employeesId == null) {
            employeesId = new LongFilter();
        }
        return employeesId;
    }

    public void setEmployeesId(LongFilter employeesId) {
        this.employeesId = employeesId;
    }

    public LongFilter getStoragesId() {
        return storagesId;
    }

    public LongFilter storagesId() {
        if (storagesId == null) {
            storagesId = new LongFilter();
        }
        return storagesId;
    }

    public void setStoragesId(LongFilter storagesId) {
        this.storagesId = storagesId;
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
            Objects.equals(resourcesId, that.resourcesId) &&
            Objects.equals(partnersId, that.partnersId) &&
            Objects.equals(businessYearsId, that.businessYearsId) &&
            Objects.equals(employeesId, that.employeesId) &&
            Objects.equals(storagesId, that.storagesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, legalEntityInfoId, resourcesId, partnersId, businessYearsId, employeesId, storagesId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (legalEntityInfoId != null ? "legalEntityInfoId=" + legalEntityInfoId + ", " : "") +
            (resourcesId != null ? "resourcesId=" + resourcesId + ", " : "") +
            (partnersId != null ? "partnersId=" + partnersId + ", " : "") +
            (businessYearsId != null ? "businessYearsId=" + businessYearsId + ", " : "") +
            (employeesId != null ? "employeesId=" + employeesId + ", " : "") +
            (storagesId != null ? "storagesId=" + storagesId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
