package inc.donau.storage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * sr: Jedinstveni maticni broj gradjana (JMBG)
     */
    @NotNull
    @Pattern(regexp = "[0-9]{13}")
    @Column(name = "unique_identification_number", nullable = false, unique = true)
    private String uniqueIdentificationNumber;

    @NotNull
    @Column(name = "birth_date", nullable = false)
    private ZonedDateTime birthDate;

    @NotNull
    @Column(name = "disability", nullable = false)
    private Boolean disability;

    @Column(name = "employment")
    private Boolean employment;

    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;

    @Column(name = "profile_picture_content_type")
    private String profilePictureContentType;

    /**
     * Cascade delete
     */
    @JsonIgnoreProperties(value = { "city", "employee", "legalEntity", "storage" }, allowSetters = true)
    @OneToOne(optional = false, cascade = CascadeType.REMOVE)
    @NotNull
    @JoinColumn(unique = true)
    private Address address;

    /**
     * Cascade delete
     */
    @JsonIgnoreProperties(value = { "contactInfo", "businessContact", "employee" }, allowSetters = true)
    @OneToOne(optional = false, cascade = CascadeType.REMOVE)
    @NotNull
    @JoinColumn(unique = true)
    private Person personalInfo;

    /**
     * Cascade delete
     */
    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User user;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "legalEntityInfo", "resources", "partners", "businessYears", "employees", "storages" },
        allowSetters = true
    )
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Employee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueIdentificationNumber() {
        return this.uniqueIdentificationNumber;
    }

    public Employee uniqueIdentificationNumber(String uniqueIdentificationNumber) {
        this.setUniqueIdentificationNumber(uniqueIdentificationNumber);
        return this;
    }

    public void setUniqueIdentificationNumber(String uniqueIdentificationNumber) {
        this.uniqueIdentificationNumber = uniqueIdentificationNumber;
    }

    public ZonedDateTime getBirthDate() {
        return this.birthDate;
    }

    public Employee birthDate(ZonedDateTime birthDate) {
        this.setBirthDate(birthDate);
        return this;
    }

    public void setBirthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getDisability() {
        return this.disability;
    }

    public Employee disability(Boolean disability) {
        this.setDisability(disability);
        return this;
    }

    public void setDisability(Boolean disability) {
        this.disability = disability;
    }

    public Boolean getEmployment() {
        return this.employment;
    }

    public Employee employment(Boolean employment) {
        this.setEmployment(employment);
        return this;
    }

    public void setEmployment(Boolean employment) {
        this.employment = employment;
    }

    public byte[] getProfilePicture() {
        return this.profilePicture;
    }

    public Employee profilePicture(byte[] profilePicture) {
        this.setProfilePicture(profilePicture);
        return this;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfilePictureContentType() {
        return this.profilePictureContentType;
    }

    public Employee profilePictureContentType(String profilePictureContentType) {
        this.profilePictureContentType = profilePictureContentType;
        return this;
    }

    public void setProfilePictureContentType(String profilePictureContentType) {
        this.profilePictureContentType = profilePictureContentType;
    }

    public Address getAddress() {
        return this.address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Employee address(Address address) {
        this.setAddress(address);
        return this;
    }

    public Person getPersonalInfo() {
        return this.personalInfo;
    }

    public void setPersonalInfo(Person person) {
        this.personalInfo = person;
    }

    public Employee personalInfo(Person person) {
        this.setPersonalInfo(person);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Employee user(User user) {
        this.setUser(user);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Employee company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", uniqueIdentificationNumber='" + getUniqueIdentificationNumber() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", disability='" + getDisability() + "'" +
            ", employment='" + getEmployment() + "'" +
            ", profilePicture='" + getProfilePicture() + "'" +
            ", profilePictureContentType='" + getProfilePictureContentType() + "'" +
            "}";
    }
}
