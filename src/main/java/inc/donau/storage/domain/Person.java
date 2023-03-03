package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import inc.donau.storage.domain.enumeration.Gender;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "maiden_name")
    private String maidenName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    /**
     * Email and phone number\nCascade delete
     */
    @JsonIgnoreProperties(value = { "person", "legalEntity" }, allowSetters = true)
    @OneToOne(optional = false, cascade = CascadeType.REMOVE)
    @NotNull
    @JoinColumn(unique = true)
    private ContactInfo contactInfo;

    /**
     * Prevent delete
     */
    @JsonIgnoreProperties(value = { "personalInfo", "businessPartner" }, allowSetters = true)
    @OneToOne(mappedBy = "personalInfo")
    private BusinessContact businessContact;

    /**
     * Prevent delete
     */
    @JsonIgnoreProperties(value = { "address", "personalInfo", "user", "company" }, allowSetters = true)
    @OneToOne(mappedBy = "personalInfo")
    private Employee employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Person id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Person firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Person middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Person lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMaidenName() {
        return this.maidenName;
    }

    public Person maidenName(String maidenName) {
        this.setMaidenName(maidenName);
        return this;
    }

    public void setMaidenName(String maidenName) {
        this.maidenName = maidenName;
    }

    public Gender getGender() {
        return this.gender;
    }

    public Person gender(Gender gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ContactInfo getContactInfo() {
        return this.contactInfo;
    }

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Person contactInfo(ContactInfo contactInfo) {
        this.setContactInfo(contactInfo);
        return this;
    }

    public BusinessContact getBusinessContact() {
        return this.businessContact;
    }

    public void setBusinessContact(BusinessContact businessContact) {
        if (this.businessContact != null) {
            this.businessContact.setPersonalInfo(null);
        }
        if (businessContact != null) {
            businessContact.setPersonalInfo(this);
        }
        this.businessContact = businessContact;
    }

    public Person businessContact(BusinessContact businessContact) {
        this.setBusinessContact(businessContact);
        return this;
    }

    public Employee getEmployee() {
        return this.employee;
    }

    public void setEmployee(Employee employee) {
        if (this.employee != null) {
            this.employee.setPersonalInfo(null);
        }
        if (employee != null) {
            employee.setPersonalInfo(this);
        }
        this.employee = employee;
    }

    public Person employee(Employee employee) {
        this.setEmployee(employee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", maidenName='" + getMaidenName() + "'" +
            ", gender='" + getGender() + "'" +
            "}";
    }
}
