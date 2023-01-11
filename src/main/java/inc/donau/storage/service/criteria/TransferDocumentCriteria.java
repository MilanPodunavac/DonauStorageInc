package inc.donau.storage.service.criteria;

import inc.donau.storage.domain.enumeration.TransferDocumentStatus;
import inc.donau.storage.domain.enumeration.TransferDocumentType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.TransferDocument} entity. This class is used
 * in {@link inc.donau.storage.web.rest.TransferDocumentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /transfer-documents?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TransferDocumentCriteria implements Serializable, Criteria {

    /**
     * Class for filtering TransferDocumentType
     */
    public static class TransferDocumentTypeFilter extends Filter<TransferDocumentType> {

        public TransferDocumentTypeFilter() {}

        public TransferDocumentTypeFilter(TransferDocumentTypeFilter filter) {
            super(filter);
        }

        @Override
        public TransferDocumentTypeFilter copy() {
            return new TransferDocumentTypeFilter(this);
        }
    }

    /**
     * Class for filtering TransferDocumentStatus
     */
    public static class TransferDocumentStatusFilter extends Filter<TransferDocumentStatus> {

        public TransferDocumentStatusFilter() {}

        public TransferDocumentStatusFilter(TransferDocumentStatusFilter filter) {
            super(filter);
        }

        @Override
        public TransferDocumentStatusFilter copy() {
            return new TransferDocumentStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TransferDocumentTypeFilter type;

    private LocalDateFilter transferDate;

    private TransferDocumentStatusFilter status;

    private LocalDateFilter accountingDate;

    private LocalDateFilter reversalDate;

    private LongFilter transferDocumentItemId;

    private LongFilter businessYearId;

    private LongFilter receivingStorageId;

    private LongFilter dispatchingStorageId;

    private LongFilter businessPartnerId;

    private Boolean distinct;

    public TransferDocumentCriteria() {}

    public TransferDocumentCriteria(TransferDocumentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.transferDate = other.transferDate == null ? null : other.transferDate.copy();
        this.status = other.status == null ? null : other.status.copy();
        this.accountingDate = other.accountingDate == null ? null : other.accountingDate.copy();
        this.reversalDate = other.reversalDate == null ? null : other.reversalDate.copy();
        this.transferDocumentItemId = other.transferDocumentItemId == null ? null : other.transferDocumentItemId.copy();
        this.businessYearId = other.businessYearId == null ? null : other.businessYearId.copy();
        this.receivingStorageId = other.receivingStorageId == null ? null : other.receivingStorageId.copy();
        this.dispatchingStorageId = other.dispatchingStorageId == null ? null : other.dispatchingStorageId.copy();
        this.businessPartnerId = other.businessPartnerId == null ? null : other.businessPartnerId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TransferDocumentCriteria copy() {
        return new TransferDocumentCriteria(this);
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

    public TransferDocumentTypeFilter getType() {
        return type;
    }

    public TransferDocumentTypeFilter type() {
        if (type == null) {
            type = new TransferDocumentTypeFilter();
        }
        return type;
    }

    public void setType(TransferDocumentTypeFilter type) {
        this.type = type;
    }

    public LocalDateFilter getTransferDate() {
        return transferDate;
    }

    public LocalDateFilter transferDate() {
        if (transferDate == null) {
            transferDate = new LocalDateFilter();
        }
        return transferDate;
    }

    public void setTransferDate(LocalDateFilter transferDate) {
        this.transferDate = transferDate;
    }

    public TransferDocumentStatusFilter getStatus() {
        return status;
    }

    public TransferDocumentStatusFilter status() {
        if (status == null) {
            status = new TransferDocumentStatusFilter();
        }
        return status;
    }

    public void setStatus(TransferDocumentStatusFilter status) {
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

    public LocalDateFilter getReversalDate() {
        return reversalDate;
    }

    public LocalDateFilter reversalDate() {
        if (reversalDate == null) {
            reversalDate = new LocalDateFilter();
        }
        return reversalDate;
    }

    public void setReversalDate(LocalDateFilter reversalDate) {
        this.reversalDate = reversalDate;
    }

    public LongFilter getTransferDocumentItemId() {
        return transferDocumentItemId;
    }

    public LongFilter transferDocumentItemId() {
        if (transferDocumentItemId == null) {
            transferDocumentItemId = new LongFilter();
        }
        return transferDocumentItemId;
    }

    public void setTransferDocumentItemId(LongFilter transferDocumentItemId) {
        this.transferDocumentItemId = transferDocumentItemId;
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

    public LongFilter getReceivingStorageId() {
        return receivingStorageId;
    }

    public LongFilter receivingStorageId() {
        if (receivingStorageId == null) {
            receivingStorageId = new LongFilter();
        }
        return receivingStorageId;
    }

    public void setReceivingStorageId(LongFilter receivingStorageId) {
        this.receivingStorageId = receivingStorageId;
    }

    public LongFilter getDispatchingStorageId() {
        return dispatchingStorageId;
    }

    public LongFilter dispatchingStorageId() {
        if (dispatchingStorageId == null) {
            dispatchingStorageId = new LongFilter();
        }
        return dispatchingStorageId;
    }

    public void setDispatchingStorageId(LongFilter dispatchingStorageId) {
        this.dispatchingStorageId = dispatchingStorageId;
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
        final TransferDocumentCriteria that = (TransferDocumentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(transferDate, that.transferDate) &&
            Objects.equals(status, that.status) &&
            Objects.equals(accountingDate, that.accountingDate) &&
            Objects.equals(reversalDate, that.reversalDate) &&
            Objects.equals(transferDocumentItemId, that.transferDocumentItemId) &&
            Objects.equals(businessYearId, that.businessYearId) &&
            Objects.equals(receivingStorageId, that.receivingStorageId) &&
            Objects.equals(dispatchingStorageId, that.dispatchingStorageId) &&
            Objects.equals(businessPartnerId, that.businessPartnerId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            type,
            transferDate,
            status,
            accountingDate,
            reversalDate,
            transferDocumentItemId,
            businessYearId,
            receivingStorageId,
            dispatchingStorageId,
            businessPartnerId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TransferDocumentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (transferDate != null ? "transferDate=" + transferDate + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (accountingDate != null ? "accountingDate=" + accountingDate + ", " : "") +
            (reversalDate != null ? "reversalDate=" + reversalDate + ", " : "") +
            (transferDocumentItemId != null ? "transferDocumentItemId=" + transferDocumentItemId + ", " : "") +
            (businessYearId != null ? "businessYearId=" + businessYearId + ", " : "") +
            (receivingStorageId != null ? "receivingStorageId=" + receivingStorageId + ", " : "") +
            (dispatchingStorageId != null ? "dispatchingStorageId=" + dispatchingStorageId + ", " : "") +
            (businessPartnerId != null ? "businessPartnerId=" + businessPartnerId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
