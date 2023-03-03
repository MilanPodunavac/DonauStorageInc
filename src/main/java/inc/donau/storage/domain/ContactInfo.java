package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContactInfo.
 */
@Entity
@Table(name = "contact_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContactInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "((\\+[0-9]{1,3})|0)[0-9]{7,10}")
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Prevent delete
     */
    @JsonIgnoreProperties(value = { "contactInfo", "businessContact", "employee" }, allowSetters = true)
    @OneToOne(mappedBy = "contactInfo")
    private Person person;

    /**
     * Prevent delete
     */
    @JsonIgnoreProperties(value = { "contactInfo", "address", "legalEntity", "company" }, allowSetters = true)
    @OneToOne(mappedBy = "contactInfo")
    private LegalEntity legalEntity;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContactInfo id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public ContactInfo email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public ContactInfo phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        if (this.person != null) {
            this.person.setContactInfo(null);
        }
        if (person != null) {
            person.setContactInfo(this);
        }
        this.person = person;
    }

    public ContactInfo person(Person person) {
        this.setPerson(person);
        return this;
    }

    public LegalEntity getLegalEntity() {
        return this.legalEntity;
    }

    public void setLegalEntity(LegalEntity legalEntity) {
        if (this.legalEntity != null) {
            this.legalEntity.setContactInfo(null);
        }
        if (legalEntity != null) {
            legalEntity.setContactInfo(this);
        }
        this.legalEntity = legalEntity;
    }

    public ContactInfo legalEntity(LegalEntity legalEntity) {
        this.setLegalEntity(legalEntity);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContactInfo)) {
            return false;
        }
        return id != null && id.equals(((ContactInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContactInfo{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            "}";
    }
}
