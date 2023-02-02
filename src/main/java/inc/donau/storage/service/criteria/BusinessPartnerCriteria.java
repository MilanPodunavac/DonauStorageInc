package inc.donau.storage.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.BusinessPartner} entity. This class is used
 * in {@link inc.donau.storage.web.rest.BusinessPartnerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /business-partners?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BusinessPartnerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter businessContactId;

    private LongFilter legalEntityInfoId;

    private LongFilter transferDocumentId;

    private LongFilter companyId;

    private Boolean distinct;

    public BusinessPartnerCriteria() {}

    public BusinessPartnerCriteria(BusinessPartnerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.businessContactId = other.businessContactId == null ? null : other.businessContactId.copy();
        this.legalEntityInfoId = other.legalEntityInfoId == null ? null : other.legalEntityInfoId.copy();
        this.transferDocumentId = other.transferDocumentId == null ? null : other.transferDocumentId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BusinessPartnerCriteria copy() {
        return new BusinessPartnerCriteria(this);
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

    public LongFilter getBusinessContactId() {
        return businessContactId;
    }

    public LongFilter businessContactId() {
        if (businessContactId == null) {
            businessContactId = new LongFilter();
        }
        return businessContactId;
    }

    public void setBusinessContactId(LongFilter businessContactId) {
        this.businessContactId = businessContactId;
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
        final BusinessPartnerCriteria that = (BusinessPartnerCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(businessContactId, that.businessContactId) &&
            Objects.equals(legalEntityInfoId, that.legalEntityInfoId) &&
            Objects.equals(transferDocumentId, that.transferDocumentId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, businessContactId, legalEntityInfoId, transferDocumentId, companyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessPartnerCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (businessContactId != null ? "businessContactId=" + businessContactId + ", " : "") +
            (legalEntityInfoId != null ? "legalEntityInfoId=" + legalEntityInfoId + ", " : "") +
            (transferDocumentId != null ? "transferDocumentId=" + transferDocumentId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
