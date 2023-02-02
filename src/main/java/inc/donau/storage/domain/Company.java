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
    @JsonIgnoreProperties(value = { "contactInfo", "address" }, allowSetters = true)
    @OneToOne(optional = false, cascade = CascadeType.REMOVE)
    @NotNull
    @JoinColumn(unique = true)
    private LegalEntity legalEntityInfo;

    /**
     * Cascade delete
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "censusItems", "transferDocumentItems", "unit", "company" }, allowSetters = true)
    private Set<Resource> resources = new HashSet<>();

    /**
     * Cascade delete
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "businessContact", "legalEntityInfo", "transferDocuments", "company" }, allowSetters = true)
    private Set<BusinessPartner> businessPartners = new HashSet<>();

    /**
     * Cascade delete
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "company" }, allowSetters = true)
    private Set<BusinessYear> businessYears = new HashSet<>();

    /**
     * Cascade delete
     */
    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "address", "personalInfo", "company" }, allowSetters = true)
    private Set<Employee> employees = new HashSet<>();

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

    public Company addResource(Resource resource) {
        this.resources.add(resource);
        resource.setCompany(this);
        return this;
    }

    public Company removeResource(Resource resource) {
        this.resources.remove(resource);
        resource.setCompany(null);
        return this;
    }

    public Set<BusinessPartner> getBusinessPartners() {
        return this.businessPartners;
    }

    public void setBusinessPartners(Set<BusinessPartner> businessPartners) {
        if (this.businessPartners != null) {
            this.businessPartners.forEach(i -> i.setCompany(null));
        }
        if (businessPartners != null) {
            businessPartners.forEach(i -> i.setCompany(this));
        }
        this.businessPartners = businessPartners;
    }

    public Company businessPartners(Set<BusinessPartner> businessPartners) {
        this.setBusinessPartners(businessPartners);
        return this;
    }

    public Company addBusinessPartner(BusinessPartner businessPartner) {
        this.businessPartners.add(businessPartner);
        businessPartner.setCompany(this);
        return this;
    }

    public Company removeBusinessPartner(BusinessPartner businessPartner) {
        this.businessPartners.remove(businessPartner);
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

    public Company addBusinessYear(BusinessYear businessYear) {
        this.businessYears.add(businessYear);
        businessYear.setCompany(this);
        return this;
    }

    public Company removeBusinessYear(BusinessYear businessYear) {
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

    public Company addEmployee(Employee employee) {
        this.employees.add(employee);
        employee.setCompany(this);
        return this;
    }

    public Company removeEmployee(Employee employee) {
        this.employees.remove(employee);
        employee.setCompany(null);
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
