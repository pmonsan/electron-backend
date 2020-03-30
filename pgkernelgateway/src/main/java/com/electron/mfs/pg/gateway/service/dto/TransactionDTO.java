package com.electron.mfs.pg.gateway.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.Transaction} entity.
 */
public class TransactionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String transactionNumber;

    @NotNull
    @Size(max = 100)
    private String label;

    @NotNull
    @DecimalMin(value = "0")
    private Double transactionAmount;

    @NotNull
    private Instant transactionDate;

    @NotNull
    private Boolean feesSupported;

    @DecimalMin(value = "0")
    private Double transactionFees;

    @NotNull
    @Size(max = 10)
    private String priceCode;

    @NotNull
    @Size(max = 5)
    private String fromPartnerCode;

    @NotNull
    @Size(max = 5)
    private String toPartnerCode;

    @NotNull
    @Size(max = 5)
    private String transactionStatusCode;

    @NotNull
    @Size(max = 5)
    private String transactionTypeCode;

    @NotNull
    @Size(max = 5)
    private String serviceCode;

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

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Boolean isFeesSupported() {
        return feesSupported;
    }

    public void setFeesSupported(Boolean feesSupported) {
        this.feesSupported = feesSupported;
    }

    public Double getTransactionFees() {
        return transactionFees;
    }

    public void setTransactionFees(Double transactionFees) {
        this.transactionFees = transactionFees;
    }

    public String getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(String priceCode) {
        this.priceCode = priceCode;
    }

    public String getFromPartnerCode() {
        return fromPartnerCode;
    }

    public void setFromPartnerCode(String fromPartnerCode) {
        this.fromPartnerCode = fromPartnerCode;
    }

    public String getToPartnerCode() {
        return toPartnerCode;
    }

    public void setToPartnerCode(String toPartnerCode) {
        this.toPartnerCode = toPartnerCode;
    }

    public String getTransactionStatusCode() {
        return transactionStatusCode;
    }

    public void setTransactionStatusCode(String transactionStatusCode) {
        this.transactionStatusCode = transactionStatusCode;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
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

        TransactionDTO transactionDTO = (TransactionDTO) o;
        if (transactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionDTO{" +
            "id=" + getId() +
            ", transactionNumber='" + getTransactionNumber() + "'" +
            ", label='" + getLabel() + "'" +
            ", transactionAmount=" + getTransactionAmount() +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", feesSupported='" + isFeesSupported() + "'" +
            ", transactionFees=" + getTransactionFees() +
            ", priceCode='" + getPriceCode() + "'" +
            ", fromPartnerCode='" + getFromPartnerCode() + "'" +
            ", toPartnerCode='" + getToPartnerCode() + "'" +
            ", transactionStatusCode='" + getTransactionStatusCode() + "'" +
            ", transactionTypeCode='" + getTransactionTypeCode() + "'" +
            ", serviceCode='" + getServiceCode() + "'" +
            ", comment='" + getComment() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
