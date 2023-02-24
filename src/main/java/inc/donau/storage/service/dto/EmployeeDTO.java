package inc.donau.storage.service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link inc.donau.storage.domain.Employee} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EmployeeDTO implements Serializable {

    private Long id;

    /**
     * sr: Jedinstveni maticni broj gradjana (JMBG)
     */
    @NotNull
    @Pattern(regexp = "[0-9]{13}")
    @Schema(description = "sr: Jedinstveni maticni broj gradjana (JMBG)", required = true)
    private String uniqueIdentificationNumber;

    @NotNull
    private ZonedDateTime birthDate;

    @NotNull
    private Boolean disability;

    private Boolean employment;

    @Lob
    private byte[] profileImage;

    private String profileImageContentType;
    private AddressDTO address;

    private PersonDTO personalInfo;

    private CompanyDTO company;

    private UserDTO user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueIdentificationNumber() {
        return uniqueIdentificationNumber;
    }

    public void setUniqueIdentificationNumber(String uniqueIdentificationNumber) {
        this.uniqueIdentificationNumber = uniqueIdentificationNumber;
    }

    public ZonedDateTime getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(ZonedDateTime birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getDisability() {
        return disability;
    }

    public void setDisability(Boolean disability) {
        this.disability = disability;
    }

    public Boolean getEmployment() {
        return employment;
    }

    public void setEmployment(Boolean employment) {
        this.employment = employment;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getProfileImageContentType() {
        return profileImageContentType;
    }

    public void setProfileImageContentType(String profileImageContentType) {
        this.profileImageContentType = profileImageContentType;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public PersonDTO getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonDTO personalInfo) {
        this.personalInfo = personalInfo;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeDTO)) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, employeeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", uniqueIdentificationNumber='" + getUniqueIdentificationNumber() + "'" +
            ", birthDate='" + getBirthDate() + "'" +
            ", disability='" + getDisability() + "'" +
            ", employment='" + getEmployment() + "'" +
            ", profileImage='" + getProfileImage() + "'" +
            ", address=" + getAddress() +
            ", personalInfo=" + getPersonalInfo() +
            ", company=" + getCompany() +
            ", user=" + getUser() +
            "}";
    }
}
