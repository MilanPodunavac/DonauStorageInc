package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * sr: Stavka popisa
 */
@Entity
@Table(name = "census_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CensusItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "amount", nullable = false)
    private Float amount;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "censusItems", "businessYear", "president", "deputy", "censusTaker", "storage" }, allowSetters = true)
    private CensusDocument censusDocument;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "censusItems", "transferDocumentItems", "unit", "company" }, allowSetters = true)
    private Resource resource;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CensusItem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getAmount() {
        return this.amount;
    }

    public CensusItem amount(Float amount) {
        this.setAmount(amount);
        return this;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public CensusDocument getCensusDocument() {
        return this.censusDocument;
    }

    public void setCensusDocument(CensusDocument censusDocument) {
        this.censusDocument = censusDocument;
    }

    public CensusItem censusDocument(CensusDocument censusDocument) {
        this.setCensusDocument(censusDocument);
        return this;
    }

    public Resource getResource() {
        return this.resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public CensusItem resource(Resource resource) {
        this.setResource(resource);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CensusItem)) {
            return false;
        }
        return id != null && id.equals(((CensusItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CensusItem{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            "}";
    }
}
