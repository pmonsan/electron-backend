package com.electron.mfs.pg.subscription.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.subscription.domain.Partner} entity.
 */
public class PartnerDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String code;

    @NotNull
    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 100)
    private String apiKey;

    @Size(max = 255)
    private String address;

    @Size(max = 100)
    private String city;

    @Size(max = 10)
    private String postalCode;

    @Size(max = 5)
    private String countryCode;

    @Size(max = 255)
    private String rsaPublicKeyPath;

    @NotNull
    @Size(max = 50)
    private String contactFirstname;

    @NotNull
    @Size(max = 50)
    private String contactLastname;

    @NotNull
    @Size(max = 100)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String businessEmail;

    @NotNull
    @Size(max = 20)
    private String businessPhone;

    @Size(max = 5)
    private String partnerTypeCode;

    @NotNull
    private Boolean active;


    private Long partnerSecurityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRsaPublicKeyPath() {
        return rsaPublicKeyPath;
    }

    public void setRsaPublicKeyPath(String rsaPublicKeyPath) {
        this.rsaPublicKeyPath = rsaPublicKeyPath;
    }

    public String getContactFirstname() {
        return contactFirstname;
    }

    public void setContactFirstname(String contactFirstname) {
        this.contactFirstname = contactFirstname;
    }

    public String getContactLastname() {
        return contactLastname;
    }

    public void setContactLastname(String contactLastname) {
        this.contactLastname = contactLastname;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getPartnerTypeCode() {
        return partnerTypeCode;
    }

    public void setPartnerTypeCode(String partnerTypeCode) {
        this.partnerTypeCode = partnerTypeCode;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getPartnerSecurityId() {
        return partnerSecurityId;
    }

    public void setPartnerSecurityId(Long partnerSecurityId) {
        this.partnerSecurityId = partnerSecurityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PartnerDTO partnerDTO = (PartnerDTO) o;
        if (partnerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), partnerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PartnerDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", apiKey='" + getApiKey() + "'" +
            ", address='" + getAddress() + "'" +
            ", city='" + getCity() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", rsaPublicKeyPath='" + getRsaPublicKeyPath() + "'" +
            ", contactFirstname='" + getContactFirstname() + "'" +
            ", contactLastname='" + getContactLastname() + "'" +
            ", businessEmail='" + getBusinessEmail() + "'" +
            ", businessPhone='" + getBusinessPhone() + "'" +
            ", partnerTypeCode='" + getPartnerTypeCode() + "'" +
            ", active='" + isActive() + "'" +
            ", partnerSecurity=" + getPartnerSecurityId() +
            "}";
    }
}
