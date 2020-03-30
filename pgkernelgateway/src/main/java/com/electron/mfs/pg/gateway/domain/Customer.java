package com.electron.mfs.pg.gateway.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Customer.
 */
@Entity
@Table(name = "t_customer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 25)
    @Column(name = "number", length = 25)
    private String number;

    @Size(max = 50)
    @Column(name = "corporate_name", length = 50)
    private String corporateName;

    @Size(max = 50)
    @Column(name = "firstname", length = 50)
    private String firstname;

    @Size(max = 50)
    @Column(name = "lastname", length = 50)
    private String lastname;

    @DecimalMin(value = "0")
    @Column(name = "gps_latitude")
    private Double gpsLatitude;

    @DecimalMin(value = "0")
    @Column(name = "gps_longitude")
    private Double gpsLongitude;

    @Size(max = 20)
    @Column(name = "home_phone", length = 20)
    private String homePhone;

    @Size(max = 20)
    @Column(name = "mobile_phone", length = 20)
    private String mobilePhone;

    @Size(max = 20)
    @Column(name = "work_phone", length = 20)
    private String workPhone;

    @Size(max = 150)
    @Column(name = "other_question", length = 150)
    private String otherQuestion;

    @Size(max = 150)
    @Column(name = "response_of_question", length = 150)
    private String responseOfQuestion;

    @Size(max = 50)
    @Column(name = "trade_register", length = 50)
    private String tradeRegister;

    @Size(max = 255)
    @Column(name = "address", length = 255)
    private String address;

    @Size(max = 10)
    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Size(max = 50)
    @Column(name = "city", length = 50)
    private String city;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email")
    private String email;

    @NotNull
    @Size(max = 5)
    @Column(name = "country_code", length = 5, nullable = false)
    private String countryCode;

    @NotNull
    @Size(max = 10)
    @Column(name = "partner_code", length = 10, nullable = false)
    private String partnerCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "activity_area_code", length = 5, nullable = false)
    private String activityAreaCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "customer_type_code", length = 5, nullable = false)
    private String customerTypeCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "question_code", length = 5, nullable = false)
    private String questionCode;

    @Size(max = 50)
    @Column(name = "username", length = 50)
    private String username;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public Customer number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public Customer corporateName(String corporateName) {
        this.corporateName = corporateName;
        return this;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getFirstname() {
        return firstname;
    }

    public Customer firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Customer lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Double getGpsLatitude() {
        return gpsLatitude;
    }

    public Customer gpsLatitude(Double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
        return this;
    }

    public void setGpsLatitude(Double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public Double getGpsLongitude() {
        return gpsLongitude;
    }

    public Customer gpsLongitude(Double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
        return this;
    }

    public void setGpsLongitude(Double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public Customer homePhone(String homePhone) {
        this.homePhone = homePhone;
        return this;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public Customer mobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
        return this;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public Customer workPhone(String workPhone) {
        this.workPhone = workPhone;
        return this;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getOtherQuestion() {
        return otherQuestion;
    }

    public Customer otherQuestion(String otherQuestion) {
        this.otherQuestion = otherQuestion;
        return this;
    }

    public void setOtherQuestion(String otherQuestion) {
        this.otherQuestion = otherQuestion;
    }

    public String getResponseOfQuestion() {
        return responseOfQuestion;
    }

    public Customer responseOfQuestion(String responseOfQuestion) {
        this.responseOfQuestion = responseOfQuestion;
        return this;
    }

    public void setResponseOfQuestion(String responseOfQuestion) {
        this.responseOfQuestion = responseOfQuestion;
    }

    public String getTradeRegister() {
        return tradeRegister;
    }

    public Customer tradeRegister(String tradeRegister) {
        this.tradeRegister = tradeRegister;
        return this;
    }

    public void setTradeRegister(String tradeRegister) {
        this.tradeRegister = tradeRegister;
    }

    public String getAddress() {
        return address;
    }

    public Customer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Customer postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public Customer city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public Customer email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public Customer countryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public Customer partnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
        return this;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getActivityAreaCode() {
        return activityAreaCode;
    }

    public Customer activityAreaCode(String activityAreaCode) {
        this.activityAreaCode = activityAreaCode;
        return this;
    }

    public void setActivityAreaCode(String activityAreaCode) {
        this.activityAreaCode = activityAreaCode;
    }

    public String getCustomerTypeCode() {
        return customerTypeCode;
    }

    public Customer customerTypeCode(String customerTypeCode) {
        this.customerTypeCode = customerTypeCode;
        return this;
    }

    public void setCustomerTypeCode(String customerTypeCode) {
        this.customerTypeCode = customerTypeCode;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public Customer questionCode(String questionCode) {
        this.questionCode = questionCode;
        return this;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getUsername() {
        return username;
    }

    public Customer username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean isActive() {
        return active;
    }

    public Customer active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        return id != null && id.equals(((Customer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Customer{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", corporateName='" + getCorporateName() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", gpsLatitude=" + getGpsLatitude() +
            ", gpsLongitude=" + getGpsLongitude() +
            ", homePhone='" + getHomePhone() + "'" +
            ", mobilePhone='" + getMobilePhone() + "'" +
            ", workPhone='" + getWorkPhone() + "'" +
            ", otherQuestion='" + getOtherQuestion() + "'" +
            ", responseOfQuestion='" + getResponseOfQuestion() + "'" +
            ", tradeRegister='" + getTradeRegister() + "'" +
            ", address='" + getAddress() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", city='" + getCity() + "'" +
            ", email='" + getEmail() + "'" +
            ", countryCode='" + getCountryCode() + "'" +
            ", partnerCode='" + getPartnerCode() + "'" +
            ", activityAreaCode='" + getActivityAreaCode() + "'" +
            ", customerTypeCode='" + getCustomerTypeCode() + "'" +
            ", questionCode='" + getQuestionCode() + "'" +
            ", username='" + getUsername() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
