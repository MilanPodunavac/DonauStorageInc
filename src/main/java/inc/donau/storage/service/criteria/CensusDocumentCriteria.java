package inc.donau.storage.service.criteria;

import inc.donau.storage.domain.enumeration.CensusDocumentStatus;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.CensusDocument} entity. This class is used
 * in {@link inc.donau.storage.web.rest.CensusDocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /census-documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CensusDocumentCriteria implements Serializable, Criteria {

    /**
     * Class for filtering CensusDocumentStatus
     */
    public static class CensusDocumentStatusFilter extends Filter<CensusDocumentStatus> {

        public CensusDocumentStatusFilter() {}

        public CensusDocumentStatusFilter(CensusDocumentStatusFilter filter) {
            super(filter);
        }

        @Override
        public CensusDocumentStatusFilter copy() {
            return new CensusDocumentStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter censusDate;

    private CensusDocumentStatusFilter status;

    private LocalDateFilter accountingDate;

    private BooleanFilter leveling;

    private LongFilter censusItemId;

    private LongFilter businessYearId;

    private LongFilter presidentId;

    private LongFilter deputyId;

    private LongFilter censusTakerId;

    private LongFilter storageId;

    private Boolean distinct;

    public CensusDocumentCriteria() {}

    public CensusDocumentCriteria(CensusDocumentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.censusDate = other.censusDate == null ? null : other.censusDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.accountingDate = other.accountingDate == null ? null : other.accountingDate.copy();
        this.leveling = other.leveling == null ? null : other.leveling.copy();
        this.censusItemId = other.censusItemId == null ? null : other.censusItemId.copy();
        this.businessYearId = other.businessYearId == null ? null : other.businessYearId.copy();
        this.presidentId = other.presidentId == null ? null : other.presidentId.copy();
        this.deputyId = other.deputyId == null ? null : other.deputyId.copy();
        this.censusTakerId = other.censusTakerId == null ? null : other.censusTakerId.copy();
        this.storageId = other.storageId == null ? null : other.storageId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public CensusDocumentCriteria copy() {
        return new CensusDocumentCriteria(this);
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

    public LocalDateFilter getCensusDate() {
        return censusDate;
    }

    public LocalDateFilter censusDate() {
        if (censusDate == null) {
            censusDate = new LocalDateFilter();
        }
        return censusDate;
    }

    public void setCensusDate(LocalDateFilter censusDate) {
        this.censusDate = censusDate;
    }

    public CensusDocumentStatusFilter getStatus() {
        return status;
    }

    public CensusDocumentStatusFilter status() {
        if (status == null) {
            status = new CensusDocumentStatusFilter();
        }
        return status;
    }

    public void setStatus(CensusDocumentStatusFilter status) {
        this.status = status;
    }

    public LocalDateFilter getAccountingDate() {
        return accountingDate;
    }

    public LocalDateFilter accountingDate() {
        if (accountingDate == null) {
            accountingDate = new LocalDateFilter();
        }
        return accountingDate;
    }

    public void setAccountingDate(LocalDateFilter accountingDate) {
        this.accountingDate = accountingDate;
    }

    public BooleanFilter getLeveling() {
        return leveling;
    }

    public BooleanFilter leveling() {
        if (leveling == null) {
            leveling = new BooleanFilter();
        }
        return leveling;
    }

    public void setLeveling(BooleanFilter leveling) {
        this.leveling = leveling;
    }

    public LongFilter getCensusItemId() {
        return censusItemId;
    }

    public LongFilter censusItemId() {
        if (censusItemId == null) {
            censusItemId = new LongFilter();
        }
        return censusItemId;
    }

    public void setCensusItemId(LongFilter censusItemId) {
        this.censusItemId = censusItemId;
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

    public LongFilter getPresidentId() {
        return presidentId;
    }

    public LongFilter presidentId() {
        if (presidentId == null) {
            presidentId = new LongFilter();
        }
        return presidentId;
    }

    public void setPresidentId(LongFilter presidentId) {
        this.presidentId = presidentId;
    }

    public LongFilter getDeputyId() {
        return deputyId;
    }

    public LongFilter deputyId() {
        if (deputyId == null) {
            deputyId = new LongFilter();
        }
        return deputyId;
    }

    public void setDeputyId(LongFilter deputyId) {
        this.deputyId = deputyId;
    }

    public LongFilter getCensusTakerId() {
        return censusTakerId;
    }

    public LongFilter censusTakerId() {
        if (censusTakerId == null) {
            censusTakerId = new LongFilter();
        }
        return censusTakerId;
    }

    public void setCensusTakerId(LongFilter censusTakerId) {
        this.censusTakerId = censusTakerId;
    }

    public LongFilter getStorageId() {
        return storageId;
    }

    public LongFilter storageId() {
        if (storageId == null) {
            storageId = new LongFilter();
        }
        return storageId;
    }

    public void setStorageId(LongFilter storageId) {
        this.storageId = storageId;
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
        final CensusDocumentCriteria that = (CensusDocumentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(censusDate, that.censusDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(accountingDate, that.accountingDate) &&
            Objects.equals(leveling, that.leveling) &&
            Objects.equals(censusItemId, that.censusItemId) &&
            Objects.equals(businessYearId, that.businessYearId) &&
            Objects.equals(presidentId, that.presidentId) &&
            Objects.equals(deputyId, that.deputyId) &&
            Objects.equals(censusTakerId, that.censusTakerId) &&
            Objects.equals(storageId, that.storageId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            censusDate,
            status,
            accountingDate,
            leveling,
            censusItemId,
            businessYearId,
            presidentId,
            deputyId,
            censusTakerId,
            storageId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CensusDocumentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (censusDate != null ? "censusDate=" + censusDate + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (accountingDate != null ? "accountingDate=" + accountingDate + ", " : "") +
            (leveling != null ? "leveling=" + leveling + ", " : "") +
            (censusItemId != null ? "censusItemId=" + censusItemId + ", " : "") +
            (businessYearId != null ? "businessYearId=" + businessYearId + ", " : "") +
            (presidentId != null ? "presidentId=" + presidentId + ", " : "") +
            (deputyId != null ? "deputyId=" + deputyId + ", " : "") +
            (censusTakerId != null ? "censusTakerId=" + censusTakerId + ", " : "") +
            (storageId != null ? "storageId=" + storageId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
