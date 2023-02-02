package inc.donau.storage.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.BusinessContact} entity. This class is used
 * in {@link inc.donau.storage.web.rest.BusinessContactResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /business-contacts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessContactCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter personalInfoId;

    private Boolean distinct;

    public BusinessContactCriteria() {}

    public BusinessContactCriteria(BusinessContactCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.personalInfoId = other.personalInfoId == null ? null : other.personalInfoId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BusinessContactCriteria copy() {
        return new BusinessContactCriteria(this);
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

    public LongFilter getPersonalInfoId() {
        return personalInfoId;
    }

    public LongFilter personalInfoId() {
        if (personalInfoId == null) {
            personalInfoId = new LongFilter();
        }
        return personalInfoId;
    }

    public void setPersonalInfoId(LongFilter personalInfoId) {
        this.personalInfoId = personalInfoId;
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
        final BusinessContactCriteria that = (BusinessContactCriteria) o;
        return (
            Objects.equals(id, that.id) && Objects.equals(personalInfoId, that.personalInfoId) && Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, personalInfoId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessContactCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (personalInfoId != null ? "personalInfoId=" + personalInfoId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
