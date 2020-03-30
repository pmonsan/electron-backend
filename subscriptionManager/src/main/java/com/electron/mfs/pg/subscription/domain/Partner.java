package com.electron.mfs.pg.subscription.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Partner.
 */
@Entity
@Table(name = "t_partner")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Partner implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 5)
    @Column(name = "code", length = 5, nullable = false)
    private String code;

    @NotNull
    @Size(max = 50)
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(max = 100)
    @Column(name = "api_key", length = 100, nullable = false)
    private String apiKey;

    @Size(max = 255)
    @Column(name = "address", length = 255)
    private String address;

    @Size(max = 100)
    @Column(name = "city", length = 100)
    private String city;

    @Size(max = 10)
    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Size(max = 5)
    @Column(name = "country_code", length = 5)
    private String countryCode;

    @Size(max = 255)
    @Column(name = "rsa_public_key_path", length = 255)
    private String rsaPublicKeyPath;

    @NotNull
    @Size(max = 50)
    @Column(name = "contact_firstname", length = 50, nullable = false)
    private String contactFirstname;

    @NotNull
    @Size(max = 50)
    @Column(name = "contact_lastname", length = 50, nullable = false)
    private String contactLastname;

    @NotNull
    @Size(max = 100)
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "business_email", length = 100, nullable = false)
    private String businessEmail;

    @NotNull
    @Size(max = 20)
    @Column(name = "business_phone", length = 20, nullable = false)
    private String businessPhone;

    @Size(max = 5)
    @Column(name = "partner_type_code", length = 5)
    private String partnerTypeCode;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("partners")
    private PartnerSecurity partnerSecurity;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Partner code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Partner name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiKey() {
        return apiKey;
    }

    public Partner apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAddress() {
        return address;
    }

    public Partner address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Partner city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Partner postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Partner countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getRsaPublicKeyPath() {
        return rsaPublicKeyPath;
    }

    public Partner rsaPublicKeyPath(String rsaPublicKeyPath) {
        this.rsaPublicKeyPath = rsaPublicKeyPath;
        return this;
    }

    public void setRsaPublicKeyPath(String rsaPublicKeyPath) {
        this.rsaPublicKeyPath = rsaPublicKeyPath;
    }

    public String getContactFirstname() {
        return contactFirstname;
    }

    public Partner contactFirstname(String contactFirstname) {
        this.contactFirstname = contactFirstname;
        return this;
    }

    public void setContactFirstname(String contactFirstname) {
        this.contactFirstname = contactFirstname;
    }

    public String getContactLastname() {
        return contactLastname;
    }

    public Partner contactLastname(String contactLastname) {
        this.contactLastname = contactLastname;
        return this;
    }

    public void setContactLastname(String contactLastname) {
        this.contactLastname = contactLastname;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public Partner businessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
        return this;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public Partner businessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
        return this;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getPartnerTypeCode() {
        return partnerTypeCode;
    }

    public Partner partnerTypeCode(String partnerTypeCode) {
        this.partnerTypeCode = partnerTypeCode;
        return this;
    }

    public void setPartnerTypeCode(String partnerTypeCode) {
        this.partnerTypeCode = partnerTypeCode;
    }

    public Boolean isActive() {
        return active;
    }

    public Partner active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public PartnerSecurity getPartnerSecurity() {
        return partnerSecurity;
    }

    public Partner partnerSecurity(PartnerSecurity partnerSecurity) {
        this.partnerSecurity = partnerSecurity;
        return this;
    }

    public void setPartnerSecurity(PartnerSecurity partnerSecurity) {
        this.partnerSecurity = partnerSecurity;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Partner)) {
            return false;
        }
        return id != null && id.equals(((Partner) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Partner{" +
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
            "}";
    }
}
