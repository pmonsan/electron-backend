package com.electron.mfs.pg.gateway.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PgAccount.
 */
@Entity
@Table(name = "t_account")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PgAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "number", length = 50, nullable = false)
    private String number;

    @NotNull
    @Column(name = "opening_date", nullable = false)
    private Instant openingDate;

    @NotNull
    @Column(name = "temporary", nullable = false)
    private Boolean temporary;

    @Column(name = "closing_date")
    private Instant closingDate;

    @Size(max = 20)
    @Column(name = "imsi", length = 20)
    private String imsi;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]{4}$")
    @Column(name = "transaction_code", nullable = false)
    private String transactionCode;

    @Column(name = "validation_date")
    private Instant validationDate;

    @NotNull
    @Size(max = 5)
    @Column(name = "account_status_code", length = 5, nullable = false)
    private String accountStatusCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "account_type_code", length = 5, nullable = false)
    private String accountTypeCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "customer_code", length = 5, nullable = false)
    private String customerCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "currency_code", length = 5, nullable = false)
    private String currencyCode;

    @Size(max = 255)
    @Column(name = "comment", length = 255)
    private String comment;

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

    public PgAccount number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Instant getOpeningDate() {
        return openingDate;
    }

    public PgAccount openingDate(Instant openingDate) {
        this.openingDate = openingDate;
        return this;
    }

    public void setOpeningDate(Instant openingDate) {
        this.openingDate = openingDate;
    }

    public Boolean isTemporary() {
        return temporary;
    }

    public PgAccount temporary(Boolean temporary) {
        this.temporary = temporary;
        return this;
    }

    public void setTemporary(Boolean temporary) {
        this.temporary = temporary;
    }

    public Instant getClosingDate() {
        return closingDate;
    }

    public PgAccount closingDate(Instant closingDate) {
        this.closingDate = closingDate;
        return this;
    }

    public void setClosingDate(Instant closingDate) {
        this.closingDate = closingDate;
    }

    public String getImsi() {
        return imsi;
    }

    public PgAccount imsi(String imsi) {
        this.imsi = imsi;
        return this;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public PgAccount transactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
        return this;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public Instant getValidationDate() {
        return validationDate;
    }

    public PgAccount validationDate(Instant validationDate) {
        this.validationDate = validationDate;
        return this;
    }

    public void setValidationDate(Instant validationDate) {
        this.validationDate = validationDate;
    }

    public String getAccountStatusCode() {
        return accountStatusCode;
    }

    public PgAccount accountStatusCode(String accountStatusCode) {
        this.accountStatusCode = accountStatusCode;
        return this;
    }

    public void setAccountStatusCode(String accountStatusCode) {
        this.accountStatusCode = accountStatusCode;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public PgAccount accountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
        return this;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public PgAccount customerCode(String customerCode) {
        this.customerCode = customerCode;
        return this;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public PgAccount currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getComment() {
        return comment;
    }

    public PgAccount comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isActive() {
        return active;
    }

    public PgAccount active(Boolean active) {
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
        if (!(o instanceof PgAccount)) {
            return false;
        }
        return id != null && id.equals(((PgAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PgAccount{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", openingDate='" + getOpeningDate() + "'" +
            ", temporary='" + isTemporary() + "'" +
            ", closingDate='" + getClosingDate() + "'" +
            ", imsi='" + getImsi() + "'" +
            ", transactionCode='" + getTransactionCode() + "'" +
            ", validationDate='" + getValidationDate() + "'" +
            ", accountStatusCode='" + getAccountStatusCode() + "'" +
            ", accountTypeCode='" + getAccountTypeCode() + "'" +
            ", customerCode='" + getCustomerCode() + "'" +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", comment='" + getComment() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
