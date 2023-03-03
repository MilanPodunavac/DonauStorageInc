package inc.donau.storage.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.Employee} entity. This class is used
 * in {@link inc.donau.storage.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter uniqueIdentificationNumber;

    private ZonedDateTimeFilter birthDate;

    private BooleanFilter disability;

    private BooleanFilter employment;

    private LongFilter addressId;

    private LongFilter personalInfoId;

    private LongFilter userId;

    private LongFilter companyId;

    private Boolean distinct;

    public EmployeeCriteria() {}

    public EmployeeCriteria(EmployeeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uniqueIdentificationNumber = other.uniqueIdentificationNumber == null ? null : other.uniqueIdentificationNumber.copy();
        this.birthDate = other.birthDate == null ? null : other.birthDate.copy();
        this.disability = other.disability == null ? null : other.disability.copy();
        this.employment = other.employment == null ? null : other.employment.copy();
        this.addressId = other.addressId == null ? null : other.addressId.copy();
        this.personalInfoId = other.personalInfoId == null ? null : other.personalInfoId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
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

    public StringFilter getUniqueIdentificationNumber() {
        return uniqueIdentificationNumber;
    }

    public StringFilter uniqueIdentificationNumber() {
        if (uniqueIdentificationNumber == null) {
            uniqueIdentificationNumber = new StringFilter();
        }
        return uniqueIdentificationNumber;
    }

    public void setUniqueIdentificationNumber(StringFilter uniqueIdentificationNumber) {
        this.uniqueIdentificationNumber = uniqueIdentificationNumber;
    }

    public ZonedDateTimeFilter getBirthDate() {
        return birthDate;
    }

    public ZonedDateTimeFilter birthDate() {
        if (birthDate == null) {
            birthDate = new ZonedDateTimeFilter();
        }
        return birthDate;
    }

    public void setBirthDate(ZonedDateTimeFilter birthDate) {
        this.birthDate = birthDate;
    }

    public BooleanFilter getDisability() {
        return disability;
    }

    public BooleanFilter disability() {
        if (disability == null) {
            disability = new BooleanFilter();
        }
        return disability;
    }

    public void setDisability(BooleanFilter disability) {
        this.disability = disability;
    }

    public BooleanFilter getEmployment() {
        return employment;
    }

    public BooleanFilter employment() {
        if (employment == null) {
            employment = new BooleanFilter();
        }
        return employment;
    }

    public void setEmployment(BooleanFilter employment) {
        this.employment = employment;
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

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
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
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(uniqueIdentificationNumber, that.uniqueIdentificationNumber) &&
            Objects.equals(birthDate, that.birthDate) &&
            Objects.equals(disability, that.disability) &&
            Objects.equals(employment, that.employment) &&
            Objects.equals(addressId, that.addressId) &&
            Objects.equals(personalInfoId, that.personalInfoId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            uniqueIdentificationNumber,
            birthDate,
            disability,
            employment,
            addressId,
            personalInfoId,
            userId,
            companyId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (uniqueIdentificationNumber != null ? "uniqueIdentificationNumber=" + uniqueIdentificationNumber + ", " : "") +
            (birthDate != null ? "birthDate=" + birthDate + ", " : "") +
            (disability != null ? "disability=" + disability + ", " : "") +
            (employment != null ? "employment=" + employment + ", " : "") +
            (addressId != null ? "addressId=" + addressId + ", " : "") +
            (personalInfoId != null ? "personalInfoId=" + personalInfoId + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
