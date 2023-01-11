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
