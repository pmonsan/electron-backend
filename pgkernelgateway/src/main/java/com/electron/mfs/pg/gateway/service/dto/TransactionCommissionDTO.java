package com.electron.mfs.pg.gateway.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.TransactionCommission} entity.
 */
public class TransactionCommissionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String code;

    @NotNull
    @Size(max = 100)
    private String label;

    @DecimalMin(value = "0")
    private Double amount;

    @NotNull
    private Boolean amountInPercent;

    @NotNull
    private Instant dateCreation;

    @DecimalMin(value = "0")
    private Double percent;

    @NotNull
    @Size(max = 5)
    private String currencyCode;

    @NotNull
    @Size(max = 5)
    private String partnerCode;

    @NotNull
    @Size(max = 5)
    private String serviceCode;

    @Size(max = 255)
    private String description;

    @DecimalMin(value = "0")
    private Double commission;


    private Long transactionId;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean isAmountInPercent() {
        return amountInPercent;
    }

    public void setAmountInPercent(Boolean amountInPercent) {
        this.amountInPercent = amountInPercent;
    }

    public Instant getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransactionCommissionDTO transactionCommissionDTO = (TransactionCommissionDTO) o;
        if (transactionCommissionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionCommissionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionCommissionDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", amount=" + getAmount() +
            ", amountInPercent='" + isAmountInPercent() + "'" +
            ", dateCreation='" + getDateCreation() + "'" +
            ", percent=" + getPercent() +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", partnerCode='" + getPartnerCode() + "'" +
            ", serviceCode='" + getServiceCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", commission=" + getCommission() +
            ", transaction=" + getTransactionId() +
            "}";
    }
}
