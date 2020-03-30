package com.electron.mfs.pg.gateway.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.PgAccount} entity.
 */
public class PgAccountDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String number;

    @NotNull
    private Instant openingDate;

    @NotNull
    private Boolean temporary;

    private Instant closingDate;

    @Size(max = 20)
    private String imsi;

    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]{4}$")
    private String transactionCode;

    private Instant validationDate;

    @NotNull
    @Size(max = 5)
    private String accountStatusCode;

    @NotNull
    @Size(max = 5)
    private String accountTypeCode;

    @NotNull
    @Size(max = 5)
    private String customerCode;

    @NotNull
    @Size(max = 5)
    private String currencyCode;

    @Size(max = 255)
    private String comment;

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

    public Instant getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Instant openingDate) {
        this.openingDate = openingDate;
    }

    public Boolean isTemporary() {
        return temporary;
    }

    public void setTemporary(Boolean temporary) {
        this.temporary = temporary;
    }

    public Instant getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Instant closingDate) {
        this.closingDate = closingDate;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getTransactionCode() {
        return transactionCode;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public Instant getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(Instant validationDate) {
        this.validationDate = validationDate;
    }

    public String getAccountStatusCode() {
        return accountStatusCode;
    }

    public void setAccountStatusCode(String accountStatusCode) {
        this.accountStatusCode = accountStatusCode;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

        PgAccountDTO pgAccountDTO = (PgAccountDTO) o;
        if (pgAccountDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pgAccountDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PgAccountDTO{" +
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
