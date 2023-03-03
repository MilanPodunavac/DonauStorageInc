package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "street_name", nullable = false)
    private String streetName;

    @NotNull
    @Pattern(regexp = "[0-9]+[a-zA-Z]?|bb|BB")
    @Column(name = "street_code", nullable = false)
    private String streetCode;

    /**
     * Local postal code
     */
    @NotNull
    @Pattern(regexp = "[0-9]{4,5}")
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "country" }, allowSetters = true)
    private City city;

    /**
     * Prevent delete
     */
    @JsonIgnoreProperties(value = { "address", "personalInfo", "user", "company" }, allowSetters = true)
    @OneToOne(mappedBy = "address")
    private Employee employee;

    /**
     * Prevent delete
     */
    @JsonIgnoreProperties(value = { "contactInfo", "address", "legalEntity", "company" }, allowSetters = true)
    @OneToOne(mappedBy = "address")
    private LegalEntity legalEntity;

    /**
     * Prevent deletion
     */
    @JsonIgnoreProperties(
        value = { "address", "storageCards", "receiveds", "dispatcheds", "censusDocuments", "company" },
        allowSetters = true
    )
    @OneToOne(mappedBy = "address")
    private Storage storage;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Address id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetName() {
        return this.streetName;
    }

    public Address streetName(String streetName) {
        this.setStreetName(streetName);
        return this;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetCode() {
        return this.streetCode;
    }

    public Address streetCode(String streetCode) {
        this.setStreetCode(streetCode);
        return this;
    }

    public void setStreetCode(String streetCode) {
        this.streetCode = streetCode;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

    public Address postalCode(String postalCode) {
        this.setPostalCode(postalCode);
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Address city(City city) {
        this.setCity(city);
        return this;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        if (this.employee != null) {
            this.employee.setAddress(null);
        }
        if (employee != null) {
            employee.setAddress(this);
        }
        this.employee = employee;
    }

    public Address employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    public LegalEntity getLegalEntity() {
        return this.legalEntity;
    }

    public void setLegalEntity(LegalEntity legalEntity) {
        if (this.legalEntity != null) {
            this.legalEntity.setAddress(null);
        }
        if (legalEntity != null) {
            legalEntity.setAddress(this);
        }
        this.legalEntity = legalEntity;
    }

    public Address legalEntity(LegalEntity legalEntity) {
        this.setLegalEntity(legalEntity);
        return this;
    }

    public Storage getStorage() {
        return this.storage;
    }

    public void setStorage(Storage storage) {
        if (this.storage != null) {
            this.storage.setAddress(null);
        }
        if (storage != null) {
            storage.setAddress(this);
        }
        this.storage = storage;
    }

    public Address storage(Storage storage) {
        this.setStorage(storage);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", streetName='" + getStreetName() + "'" +
            ", streetCode='" + getStreetCode() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            "}";
    }
}
