package com.electron.mfs.pg.feescommission.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A SubscriptionPrice.
 */
@Entity
@Table(name = "t_subscription_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SubscriptionPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "0")
    @Column(name = "amount")
    private Double amount;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Size(max = 100)
    @Column(name = "label", length = 100)
    private String label;

    @Column(name = "modification_date")
    private Instant modificationDate;

    @NotNull
    @Size(max = 10)
    @Column(name = "service_code", length = 10, nullable = false)
    private String serviceCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "account_type_code", length = 5, nullable = false)
    private String accountTypeCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "currency_code", length = 5, nullable = false)
    private String currencyCode;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("subscriptionPrices")
    private PricePlan pricePlan;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public SubscriptionPrice amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public SubscriptionPrice description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public SubscriptionPrice label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public SubscriptionPrice modificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public SubscriptionPrice serviceCode(String serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public SubscriptionPrice accountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
        return this;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public SubscriptionPrice currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Boolean isActive() {
        return active;
    }

    public SubscriptionPrice active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public PricePlan getPricePlan() {
        return pricePlan;
    }

    public SubscriptionPrice pricePlan(PricePlan pricePlan) {
        this.pricePlan = pricePlan;
        return this;
    }

    public void setPricePlan(PricePlan pricePlan) {
        this.pricePlan = pricePlan;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubscriptionPrice)) {
            return false;
        }
        return id != null && id.equals(((SubscriptionPrice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SubscriptionPrice{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", description='" + getDescription() + "'" +
            ", label='" + getLabel() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            ", serviceCode='" + getServiceCode() + "'" +
            ", accountTypeCode='" + getAccountTypeCode() + "'" +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
