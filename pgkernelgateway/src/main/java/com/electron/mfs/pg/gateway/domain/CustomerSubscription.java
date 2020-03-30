package com.electron.mfs.pg.gateway.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A CustomerSubscription.
 */
@Entity
@Table(name = "t_customer_subscription")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 25)
    @Column(name = "number", length = 25)
    private String number;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Column(name = "is_merchant_subscription", nullable = false)
    private Boolean isMerchantSubscription;

    @NotNull
    @Column(name = "modification_date", nullable = false)
    private Instant modificationDate;

    @Column(name = "validation_date")
    private Instant validationDate;

    @NotNull
    @Size(max = 100)
    @Column(name = "filename", length = 100, nullable = false)
    private String filename;

    @NotNull
    @Size(max = 5)
    @Column(name = "customer_code", length = 5, nullable = false)
    private String customerCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "service_code", length = 5, nullable = false)
    private String serviceCode;

    @Size(max = 50)
    @Column(name = "account_number", length = 50)
    private String accountNumber;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Instant startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Instant endDate;

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

    public CustomerSubscription number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public CustomerSubscription creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isIsMerchantSubscription() {
        return isMerchantSubscription;
    }

    public CustomerSubscription isMerchantSubscription(Boolean isMerchantSubscription) {
        this.isMerchantSubscription = isMerchantSubscription;
        return this;
    }

    public void setIsMerchantSubscription(Boolean isMerchantSubscription) {
        this.isMerchantSubscription = isMerchantSubscription;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public CustomerSubscription modificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Instant getValidationDate() {
        return validationDate;
    }

    public CustomerSubscription validationDate(Instant validationDate) {
        this.validationDate = validationDate;
        return this;
    }

    public void setValidationDate(Instant validationDate) {
        this.validationDate = validationDate;
    }

    public String getFilename() {
        return filename;
    }

    public CustomerSubscription filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public CustomerSubscription customerCode(String customerCode) {
        this.customerCode = customerCode;
        return this;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public CustomerSubscription serviceCode(String serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public CustomerSubscription accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public CustomerSubscription startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public CustomerSubscription endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Boolean isActive() {
        return active;
    }

    public CustomerSubscription active(Boolean active) {
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
        if (!(o instanceof CustomerSubscription)) {
            return false;
        }
        return id != null && id.equals(((CustomerSubscription) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CustomerSubscription{" +
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
