package com.electron.mfs.pg.customer.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.customer.domain.Customer} entity.
 */
public class CustomerDTO implements Serializable {

    private Long id;

    @Size(max = 25)
    private String number;

    @Size(max = 50)
    private String corporateName;

    @Size(max = 50)
    private String firstname;

    @Size(max = 50)
    private String lastname;

    @DecimalMin(value = "0")
    private Double gpsLatitude;

    @DecimalMin(value = "0")
    private Double gpsLongitude;

    @Size(max = 20)
    private String homePhone;

    @Size(max = 20)
    private String mobilePhone;

    @Size(max = 20)
    private String workPhone;

    @Size(max = 150)
    private String otherQuestion;

    @Size(max = 150)
    private String responseOfQuestion;

    @Size(max = 50)
    private String tradeRegister;

    @Size(max = 255)
    private String address;

    @Size(max = 10)
    private String postalCode;

    @Size(max = 50)
    private String city;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email;

    @NotNull
    @Size(max = 5)
    private String countryCode;

    @NotNull
    @Size(max = 10)
    private String partnerCode;

    @NotNull
    @Size(max = 5)
    private String activityAreaCode;

    @NotNull
    @Size(max = 5)
    private String customerTypeCode;

    @NotNull
    @Size(max = 5)
    private String questionCode;

    @Size(max = 50)
    private String username;

    @NotNull
    private Boolean active;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Double getGpsLatitude() {
        return gpsLatitude;
    }

    public void setGpsLatitude(Double gpsLatitude) {
        this.gpsLatitude = gpsLatitude;
    }

    public Double getGpsLongitude() {
        return gpsLongitude;
    }

    public void setGpsLongitude(Double gpsLongitude) {
        this.gpsLongitude = gpsLongitude;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getOtherQuestion() {
        return otherQuestion;
    }

    public void setOtherQuestion(String otherQuestion) {
        this.otherQuestion = otherQuestion;
    }

    public String getResponseOfQuestion() {
        return responseOfQuestion;
    }

    public void setResponseOfQuestion(String responseOfQuestion) {
        this.responseOfQuestion = responseOfQuestion;
    }

    public String getTradeRegister() {
        return tradeRegister;
    }

    public void setTradeRegister(String tradeRegister) {
        this.tradeRegister = tradeRegister;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getActivityAreaCode() {
        return activityAreaCode;
    }

    public void setActivityAreaCode(String activityAreaCode) {
        this.activityAreaCode = activityAreaCode;
    }

    public String getCustomerTypeCode() {
        return customerTypeCode;
    }

    public void setCustomerTypeCode(String customerTypeCode) {
        this.customerTypeCode = customerTypeCode;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerDTO customerDTO = (CustomerDTO) o;
        if (customerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
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
