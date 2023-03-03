package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * Cascade delete
     */
    @JsonIgnoreProperties(value = { "contactInfo", "address", "legalEntity", "company" }, allowSetters = true)
    @OneToOne(optional = false, cascade = CascadeType.REMOVE)
    @NotNull
    @JoinColumn(unique = true)
    private LegalEntity legalEntityInfo;

    /**
     * Prevent delete
     */
    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "censusItems", "transferItems", "unit", "company", "storageCards" }, allowSetters = true)
    private Set<Resource> resources = new HashSet<>();

    /**
     * Prevent delete
     */
    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "businessContact", "legalEntityInfo", "transfers", "company" }, allowSetters = true)
    private Set<BusinessPartner> partners = new HashSet<>();

    /**
     * Prevent delete
     */
    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "company", "censusDocuments", "storageCards", "transfers" }, allowSetters = true)
    private Set<BusinessYear> businessYears = new HashSet<>();

    /**
     * Prevent delete
     */
    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "address", "personalInfo", "user", "company" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

    /**
     * Prevent deletion
     */
    @OneToMany(mappedBy = "company")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "address", "storageCards", "receiveds", "dispatcheds", "censusDocuments", "company" },
        allowSetters = true
    )
    private Set<Storage> storages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Company id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LegalEntity getLegalEntityInfo() {
        return this.legalEntityInfo;
    }

    public void setLegalEntityInfo(LegalEntity legalEntity) {
        this.legalEntityInfo = legalEntity;
    }

    public Company legalEntityInfo(LegalEntity legalEntity) {
        this.setLegalEntityInfo(legalEntity);
        return this;
    }

    public Set<Resource> getResources() {
        return this.resources;
    }

    public void setResources(Set<Resource> resources) {
        if (this.resources != null) {
            this.resources.forEach(i -> i.setCompany(null));
        }
        if (resources != null) {
            resources.forEach(i -> i.setCompany(this));
        }
        this.resources = resources;
    }

    public Company resources(Set<Resource> resources) {
        this.setResources(resources);
        return this;
    }

    public Company addResources(Resource resource) {
        this.resources.add(resource);
        resource.setCompany(this);
        return this;
    }

    public Company removeResources(Resource resource) {
        this.resources.remove(resource);
        resource.setCompany(null);
        return this;
    }

    public Set<BusinessPartner> getPartners() {
        return this.partners;
    }

    public void setPartners(Set<BusinessPartner> businessPartners) {
        if (this.partners != null) {
            this.partners.forEach(i -> i.setCompany(null));
        }
        if (businessPartners != null) {
            businessPartners.forEach(i -> i.setCompany(this));
        }
        this.partners = businessPartners;
    }

    public Company partners(Set<BusinessPartner> businessPartners) {
        this.setPartners(businessPartners);
        return this;
    }

    public Company addPartners(BusinessPartner businessPartner) {
        this.partners.add(businessPartner);
        businessPartner.setCompany(this);
        return this;
    }

    public Company removePartners(BusinessPartner businessPartner) {
        this.partners.remove(businessPartner);
        businessPartner.setCompany(null);
        return this;
    }

    public Set<BusinessYear> getBusinessYears() {
        return this.businessYears;
    }

    public void setBusinessYears(Set<BusinessYear> businessYears) {
        if (this.businessYears != null) {
            this.businessYears.forEach(i -> i.setCompany(null));
        }
        if (businessYears != null) {
            businessYears.forEach(i -> i.setCompany(this));
        }
        this.businessYears = businessYears;
    }

    public Company businessYears(Set<BusinessYear> businessYears) {
        this.setBusinessYears(businessYears);
        return this;
    }

    public Company addBusinessYears(BusinessYear businessYear) {
        this.businessYears.add(businessYear);
        businessYear.setCompany(this);
        return this;
    }

    public Company removeBusinessYears(BusinessYear businessYear) {
        this.businessYears.remove(businessYear);
        businessYear.setCompany(null);
        return this;
    }

    public Set<Employee> getEmployees() {
        return this.employees;
    }

    public void setEmployees(Set<Employee> employees) {
        if (this.employees != null) {
            this.employees.forEach(i -> i.setCompany(null));
        }
        if (employees != null) {
            employees.forEach(i -> i.setCompany(this));
        }
        this.employees = employees;
    }

    public Company employees(Set<Employee> employees) {
        this.setEmployees(employees);
        return this;
    }

    public Company addEmployees(Employee employee) {
        this.employees.add(employee);
        employee.setCompany(this);
        return this;
    }

    public Company removeEmployees(Employee employee) {
        this.employees.remove(employee);
        employee.setCompany(null);
        return this;
    }

    public Set<Storage> getStorages() {
        return this.storages;
    }

    public void setStorages(Set<Storage> storages) {
        if (this.storages != null) {
            this.storages.forEach(i -> i.setCompany(null));
        }
        if (storages != null) {
            storages.forEach(i -> i.setCompany(this));
        }
        this.storages = storages;
    }

    public Company storages(Set<Storage> storages) {
        this.setStorages(storages);
        return this;
    }

    public Company addStorages(Storage storage) {
        this.storages.add(storage);
        storage.setCompany(this);
        return this;
    }

    public Company removeStorages(Storage storage) {
        this.storages.remove(storage);
        storage.setCompany(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            "}";
    }
}
