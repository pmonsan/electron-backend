package com.electron.mfs.pg.subscription.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.subscription.domain.CustomerSubscription} entity.
 */
public class CustomerSubscriptionDTO implements Serializable {

    private Long id;

    @Size(max = 25)
    private String number;

    @NotNull
    private Instant creationDate;

    @NotNull
    private Boolean isMerchantSubscription;

    @NotNull
    private Instant modificationDate;

    private Instant validationDate;

    @NotNull
    @Size(max = 100)
    private String filename;

    @NotNull
    @Size(max = 5)
    private String customerCode;

    @NotNull
    @Size(max = 5)
    private String serviceCode;

    @Size(max = 50)
    private String accountNumber;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

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

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isIsMerchantSubscription() {
        return isMerchantSubscription;
    }

    public void setIsMerchantSubscription(Boolean isMerchantSubscription) {
        this.isMerchantSubscription = isMerchantSubscription;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Instant getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(Instant validationDate) {
        this.validationDate = validationDate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
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

        CustomerSubscriptionDTO customerSubscriptionDTO = (CustomerSubscriptionDTO) o;
        if (customerSubscriptionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerSubscriptionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerSubscriptionDTO{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", isMerchantSubscription='" + isIsMerchantSubscription() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            ", validationDate='" + getValidationDate() + "'" +
            ", filename='" + getFilename() + "'" +
            ", customerCode='" + getCustomerCode() + "'" +
            ", serviceCode='" + getServiceCode() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
