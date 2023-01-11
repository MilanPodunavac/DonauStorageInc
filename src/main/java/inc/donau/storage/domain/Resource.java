package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import inc.donau.storage.domain.enumeration.ResourceType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Resource.
 */
@Entity
@Table(name = "resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ResourceType type;

    /**
     * Prevent deletion
     */
    @OneToMany(mappedBy = "resource")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "censusDocument", "resource" }, allowSetters = true)
    private Set<CensusItem> censusItems = new HashSet<>();

    /**
     * Prevent deletion
     */
    @OneToMany(mappedBy = "resource")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "transferDocument", "resource" }, allowSetters = true)
    private Set<TransferDocumentItem> transferDocumentItems = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    private MeasurementUnit unit;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "legalEntityInfo", "resources", "businessPartners", "businessYears", "employees" }, allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Resource id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Resource name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceType getType() {
        return this.type;
    }

    public Resource type(ResourceType type) {
        this.setType(type);
        return this;
    }

    public void setType(ResourceType type) {
        this.type = type;
    }

    public Set<CensusItem> getCensusItems() {
        return this.censusItems;
    }

    public void setCensusItems(Set<CensusItem> censusItems) {
        if (this.censusItems != null) {
            this.censusItems.forEach(i -> i.setResource(null));
        }
        if (censusItems != null) {
            censusItems.forEach(i -> i.setResource(this));
        }
        this.censusItems = censusItems;
    }

    public Resource censusItems(Set<CensusItem> censusItems) {
        this.setCensusItems(censusItems);
        return this;
    }

    public Resource addCensusItem(CensusItem censusItem) {
        this.censusItems.add(censusItem);
        censusItem.setResource(this);
        return this;
    }

    public Resource removeCensusItem(CensusItem censusItem) {
        this.censusItems.remove(censusItem);
        censusItem.setResource(null);
        return this;
    }

    public Set<TransferDocumentItem> getTransferDocumentItems() {
        return this.transferDocumentItems;
    }

    public void setTransferDocumentItems(Set<TransferDocumentItem> transferDocumentItems) {
        if (this.transferDocumentItems != null) {
            this.transferDocumentItems.forEach(i -> i.setResource(null));
        }
        if (transferDocumentItems != null) {
            transferDocumentItems.forEach(i -> i.setResource(this));
        }
        this.transferDocumentItems = transferDocumentItems;
    }

    public Resource transferDocumentItems(Set<TransferDocumentItem> transferDocumentItems) {
        this.setTransferDocumentItems(transferDocumentItems);
        return this;
    }

    public Resource addTransferDocumentItem(TransferDocumentItem transferDocumentItem) {
        this.transferDocumentItems.add(transferDocumentItem);
        transferDocumentItem.setResource(this);
        return this;
    }

    public Resource removeTransferDocumentItem(TransferDocumentItem transferDocumentItem) {
        this.transferDocumentItems.remove(transferDocumentItem);
        transferDocumentItem.setResource(null);
        return this;
    }

    public MeasurementUnit getUnit() {
        return this.unit;
    }

    public void setUnit(MeasurementUnit measurementUnit) {
        this.unit = measurementUnit;
    }

    public Resource unit(MeasurementUnit measurementUnit) {
        this.setUnit(measurementUnit);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Resource company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Resource)) {
            return false;
        }
        return id != null && id.equals(((Resource) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Resource{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
