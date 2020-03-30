package com.electron.mfs.pg.gateway.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.Price} entity.
 */
public class PriceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
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
    private String currencyCode;

    @NotNull
    @Size(max = 5)
    private String serviceCode;

    @Size(max = 255)
    private String description;

    private Instant modificationDate;

    @NotNull
    private Boolean active;


    private Long pricePlanId;

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

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getPricePlanId() {
        return pricePlanId;
    }

    public void setPricePlanId(Long pricePlanId) {
        this.pricePlanId = pricePlanId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriceDTO priceDTO = (PriceDTO) o;
        if (priceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), priceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PriceDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", amount=" + getAmount() +
            ", percent=" + getPercent() +
            ", amountInPercent='" + isAmountInPercent() + "'" +
            ", amountTransactionMax=" + getAmountTransactionMax() +
            ", amountTransactionMin=" + getAmountTransactionMin() +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", serviceCode='" + getServiceCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            ", active='" + isActive() + "'" +
            ", pricePlan=" + getPricePlanId() +
            "}";
    }
}
