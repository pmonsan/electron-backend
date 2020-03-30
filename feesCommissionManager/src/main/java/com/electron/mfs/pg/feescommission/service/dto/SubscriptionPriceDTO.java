package com.electron.mfs.pg.feescommission.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.feescommission.domain.SubscriptionPrice} entity.
 */
public class SubscriptionPriceDTO implements Serializable {

    private Long id;

    @DecimalMin(value = "0")
    private Double amount;

    @Size(max = 255)
    private String description;

    @Size(max = 100)
    private String label;

    private Instant modificationDate;

    @NotNull
    @Size(max = 10)
    private String serviceCode;

    @NotNull
    @Size(max = 5)
    private String accountTypeCode;

    @NotNull
    @Size(max = 5)
    private String currencyCode;

    @NotNull
    private Boolean active;


    private Long pricePlanId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

        SubscriptionPriceDTO subscriptionPriceDTO = (SubscriptionPriceDTO) o;
        if (subscriptionPriceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), subscriptionPriceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SubscriptionPriceDTO{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", description='" + getDescription() + "'" +
            ", label='" + getLabel() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            ", serviceCode='" + getServiceCode() + "'" +
            ", accountTypeCode='" + getAccountTypeCode() + "'" +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", active='" + isActive() + "'" +
            ", pricePlan=" + getPricePlanId() +
            "}";
    }
}
