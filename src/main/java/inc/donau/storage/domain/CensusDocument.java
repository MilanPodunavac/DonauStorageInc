package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import inc.donau.storage.domain.enumeration.CensusDocumentStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * sr: Popisni dokument
 */
@Entity
@Table(name = "census_document")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CensusDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * sr: Popis
     */
    @NotNull
    @Column(name = "census_date", nullable = false)
    private LocalDate censusDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CensusDocumentStatus status;

    /**
     * sr: Knjizenje
     */
    @Column(name = "accounting_date")
    private LocalDate accountingDate;

    @Column(name = "leveling")
    private Boolean leveling;

    @OneToMany(mappedBy = "censusDocument", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "censusDocument", "resource" }, allowSetters = true)
    private Set<CensusItem> censusItems = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "company", "censusDocuments", "storageCards", "transfers" }, allowSetters = true)
    private BusinessYear businessYear;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "address", "personalInfo", "user", "company" }, allowSetters = true)
    private Employee president;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "address", "personalInfo", "user", "company" }, allowSetters = true)
    private Employee deputy;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "address", "personalInfo", "user", "company" }, allowSetters = true)
    private Employee censusTaker;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "address", "storageCards", "receiveds", "dispatcheds", "censusDocuments", "company" },
        allowSetters = true
    )
    private Storage storage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CensusDocument id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCensusDate() {
        return this.censusDate;
    }

    public CensusDocument censusDate(LocalDate censusDate) {
        this.setCensusDate(censusDate);
        return this;
    }

    public void setCensusDate(LocalDate censusDate) {
        this.censusDate = censusDate;
    }

    public CensusDocumentStatus getStatus() {
        return this.status;
    }

    public CensusDocument status(CensusDocumentStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CensusDocumentStatus status) {
        this.status = status;
    }

    public LocalDate getAccountingDate() {
        return this.accountingDate;
    }

    public CensusDocument accountingDate(LocalDate accountingDate) {
        this.setAccountingDate(accountingDate);
        return this;
    }

    public void setAccountingDate(LocalDate accountingDate) {
        this.accountingDate = accountingDate;
    }

    public Boolean getLeveling() {
        return this.leveling;
    }

    public CensusDocument leveling(Boolean leveling) {
        this.setLeveling(leveling);
        return this;
    }

    public void setLeveling(Boolean leveling) {
        this.leveling = leveling;
    }

    public Set<CensusItem> getCensusItems() {
        return this.censusItems;
    }

    public void setCensusItems(Set<CensusItem> censusItems) {
        if (this.censusItems != null) {
            this.censusItems.forEach(i -> i.setCensusDocument(null));
        }
        if (censusItems != null) {
            censusItems.forEach(i -> i.setCensusDocument(this));
        }
        this.censusItems = censusItems;
    }

    public CensusDocument censusItems(Set<CensusItem> censusItems) {
        this.setCensusItems(censusItems);
        return this;
    }

    public CensusDocument addCensusItem(CensusItem censusItem) {
        this.censusItems.add(censusItem);
        censusItem.setCensusDocument(this);
        return this;
    }

    public CensusDocument removeCensusItem(CensusItem censusItem) {
        this.censusItems.remove(censusItem);
        censusItem.setCensusDocument(null);
        return this;
    }

    public BusinessYear getBusinessYear() {
        return this.businessYear;
    }

    public void setBusinessYear(BusinessYear businessYear) {
        this.businessYear = businessYear;
    }

    public CensusDocument businessYear(BusinessYear businessYear) {
        this.setBusinessYear(businessYear);
        return this;
    }

    public Employee getPresident() {
        return this.president;
    }

    public void setPresident(Employee employee) {
        this.president = employee;
    }

    public CensusDocument president(Employee employee) {
        this.setPresident(employee);
        return this;
    }

    public Employee getDeputy() {
        return this.deputy;
    }

    public void setDeputy(Employee employee) {
        this.deputy = employee;
    }

    public CensusDocument deputy(Employee employee) {
        this.setDeputy(employee);
        return this;
    }

    public Employee getCensusTaker() {
        return this.censusTaker;
    }

    public void setCensusTaker(Employee employee) {
        this.censusTaker = employee;
    }

    public CensusDocument censusTaker(Employee employee) {
        this.setCensusTaker(employee);
        return this;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public CensusDocument storage(Storage storage) {
        this.setStorage(storage);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CensusDocument)) {
            return false;
        }
        return id != null && id.equals(((CensusDocument) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CensusDocument{" +
            "id=" + getId() +
            ", censusDate='" + getCensusDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", accountingDate='" + getAccountingDate() + "'" +
            ", leveling='" + getLeveling() + "'" +
            "}";
    }
}
