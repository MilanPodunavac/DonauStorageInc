package inc.donau.storage.service.criteria;

import inc.donau.storage.domain.enumeration.ResourceType;
import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link inc.donau.storage.domain.Resource} entity. This class is used
 * in {@link inc.donau.storage.web.rest.ResourceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /resources?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ResourceCriteria implements Serializable, Criteria {

    /**
     * Class for filtering ResourceType
     */
    public static class ResourceTypeFilter extends Filter<ResourceType> {

        public ResourceTypeFilter() {}

        public ResourceTypeFilter(ResourceTypeFilter filter) {
            super(filter);
        }

        @Override
        public ResourceTypeFilter copy() {
            return new ResourceTypeFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private ResourceTypeFilter type;

    private LongFilter censusItemId;

    private LongFilter transferDocumentItemId;

    private LongFilter unitId;

    private LongFilter companyId;

    private Boolean distinct;

    public ResourceCriteria() {}

    public ResourceCriteria(ResourceCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.censusItemId = other.censusItemId == null ? null : other.censusItemId.copy();
        this.transferDocumentItemId = other.transferDocumentItemId == null ? null : other.transferDocumentItemId.copy();
        this.unitId = other.unitId == null ? null : other.unitId.copy();
        this.companyId = other.companyId == null ? null : other.companyId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public ResourceCriteria copy() {
        return new ResourceCriteria(this);
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

    public StringFilter getName() {
        return name;
    }

    public StringFilter name() {
        if (name == null) {
            name = new StringFilter();
        }
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public ResourceTypeFilter getType() {
        return type;
    }

    public ResourceTypeFilter type() {
        if (type == null) {
            type = new ResourceTypeFilter();
        }
        return type;
    }

    public void setType(ResourceTypeFilter type) {
        this.type = type;
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

    public LongFilter getUnitId() {
        return unitId;
    }

    public LongFilter unitId() {
        if (unitId == null) {
            unitId = new LongFilter();
        }
        return unitId;
    }

    public void setUnitId(LongFilter unitId) {
        this.unitId = unitId;
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
        final ResourceCriteria that = (ResourceCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(type, that.type) &&
            Objects.equals(censusItemId, that.censusItemId) &&
            Objects.equals(transferDocumentItemId, that.transferDocumentItemId) &&
            Objects.equals(unitId, that.unitId) &&
            Objects.equals(companyId, that.companyId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, censusItemId, transferDocumentItemId, unitId, companyId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResourceCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (censusItemId != null ? "censusItemId=" + censusItemId + ", " : "") +
            (transferDocumentItemId != null ? "transferDocumentItemId=" + transferDocumentItemId + ", " : "") +
            (unitId != null ? "unitId=" + unitId + ", " : "") +
            (companyId != null ? "companyId=" + companyId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
