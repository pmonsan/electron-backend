package com.electron.mfs.pg.transactions.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.transactions.domain.TransactionPrice} entity.
 */
public class TransactionPriceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String code;

    @NotNull
    @Size(max = 100)
    private String label;

    @DecimalMin(value = "0")
    private Double amount;

    @DecimalMin(value = "0")
    private Double percent;

    @NotNull
    private Boolean amountInPercent;

    @DecimalMin(value = "0")
    private Double amountTransactionMax;

    @DecimalMin(value = "0")
    private Double amountTransactionMin;

    @NotNull
    @Size(max = 5)
    private String priceCode;

    @NotNull
    @Size(max = 5)
    private String serviceCode;

    @Size(max = 255)
    private String description;

    private Instant modificationDate;


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

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Boolean isAmountInPercent() {
        return amountInPercent;
    }

    public void setAmountInPercent(Boolean amountInPercent) {
        this.amountInPercent = amountInPercent;
    }

    public Double getAmountTransactionMax() {
        return amountTransactionMax;
    }

    public void setAmountTransactionMax(Double amountTransactionMax) {
        this.amountTransactionMax = amountTransactionMax;
    }

    public Double getAmountTransactionMin() {
        return amountTransactionMin;
    }

    public void setAmountTransactionMin(Double amountTransactionMin) {
        this.amountTransactionMin = amountTransactionMin;
    }

    public String getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(String priceCode) {
        this.priceCode = priceCode;
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

    public Instant getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
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

        TransactionPriceDTO transactionPriceDTO = (TransactionPriceDTO) o;
        if (transactionPriceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionPriceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionPriceDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", amount=" + getAmount() +
            ", percent=" + getPercent() +
            ", amountInPercent='" + isAmountInPercent() + "'" +
            ", amountTransactionMax=" + getAmountTransactionMax() +
            ", amountTransactionMin=" + getAmountTransactionMin() +
            ", priceCode='" + getPriceCode() + "'" +
            ", serviceCode='" + getServiceCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            ", transaction=" + getTransactionId() +
            "}";
    }
}
