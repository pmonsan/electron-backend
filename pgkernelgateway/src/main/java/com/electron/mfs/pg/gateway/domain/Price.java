package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Price.
 */
@Entity
@Table(name = "t_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Price implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 5)
    @Column(name = "code", length = 5, nullable = false)
    private String code;

    @NotNull
    @Size(max = 100)
    @Column(name = "label", length = 100, nullable = false)
    private String label;

    @DecimalMin(value = "0")
    @Column(name = "amount")
    private Double amount;

    @DecimalMin(value = "0")
    @Column(name = "percent")
    private Double percent;

    @NotNull
    @Column(name = "amount_in_percent", nullable = false)
    private Boolean amountInPercent;

    @DecimalMin(value = "0")
    @Column(name = "amount_transaction_max")
    private Double amountTransactionMax;

    @DecimalMin(value = "0")
    @Column(name = "amount_transaction_min")
    private Double amountTransactionMin;

    @NotNull
    @Size(max = 5)
    @Column(name = "currency_code", length = 5, nullable = false)
    private String currencyCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "service_code", length = 5, nullable = false)
    private String serviceCode;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "modification_date")
    private Instant modificationDate;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("prices")
    private PricePlan pricePlan;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Price code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public Price label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getAmount() {
        return amount;
    }

    public Price amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPercent() {
        return percent;
    }

    public Price percent(Double percent) {
        this.percent = percent;
        return this;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Boolean isAmountInPercent() {
        return amountInPercent;
    }

    public Price amountInPercent(Boolean amountInPercent) {
        this.amountInPercent = amountInPercent;
        return this;
    }

    public void setAmountInPercent(Boolean amountInPercent) {
        this.amountInPercent = amountInPercent;
    }

    public Double getAmountTransactionMax() {
        return amountTransactionMax;
    }

    public Price amountTransactionMax(Double amountTransactionMax) {
        this.amountTransactionMax = amountTransactionMax;
        return this;
    }

    public void setAmountTransactionMax(Double amountTransactionMax) {
        this.amountTransactionMax = amountTransactionMax;
    }

    public Double getAmountTransactionMin() {
        return amountTransactionMin;
    }

    public Price amountTransactionMin(Double amountTransactionMin) {
        this.amountTransactionMin = amountTransactionMin;
        return this;
    }

    public void setAmountTransactionMin(Double amountTransactionMin) {
        this.amountTransactionMin = amountTransactionMin;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Price currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public Price serviceCode(String serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getDescription() {
        return description;
    }

    public Price description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public Price modificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Boolean isActive() {
        return active;
    }

    public Price active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public PricePlan getPricePlan() {
        return pricePlan;
    }

    public Price pricePlan(PricePlan pricePlan) {
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
        if (!(o instanceof Price)) {
            return false;
        }
        return id != null && id.equals(((Price) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Price{" +
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
            "}";
    }
}
