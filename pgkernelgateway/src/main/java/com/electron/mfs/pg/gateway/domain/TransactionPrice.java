package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TransactionPrice.
 */
@Entity
@Table(name = "t_transaction_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransactionPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "code", length = 10, nullable = false)
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
    @Column(name = "price_code", length = 5, nullable = false)
    private String priceCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "service_code", length = 5, nullable = false)
    private String serviceCode;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "modification_date")
    private Instant modificationDate;

    @ManyToOne
    @JsonIgnoreProperties("transactionPrices")
    private Transaction transaction;

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

    public TransactionPrice code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public TransactionPrice label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getAmount() {
        return amount;
    }

    public TransactionPrice amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getPercent() {
        return percent;
    }

    public TransactionPrice percent(Double percent) {
        this.percent = percent;
        return this;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public Boolean isAmountInPercent() {
        return amountInPercent;
    }

    public TransactionPrice amountInPercent(Boolean amountInPercent) {
        this.amountInPercent = amountInPercent;
        return this;
    }

    public void setAmountInPercent(Boolean amountInPercent) {
        this.amountInPercent = amountInPercent;
    }

    public Double getAmountTransactionMax() {
        return amountTransactionMax;
    }

    public TransactionPrice amountTransactionMax(Double amountTransactionMax) {
        this.amountTransactionMax = amountTransactionMax;
        return this;
    }

    public void setAmountTransactionMax(Double amountTransactionMax) {
        this.amountTransactionMax = amountTransactionMax;
    }

    public Double getAmountTransactionMin() {
        return amountTransactionMin;
    }

    public TransactionPrice amountTransactionMin(Double amountTransactionMin) {
        this.amountTransactionMin = amountTransactionMin;
        return this;
    }

    public void setAmountTransactionMin(Double amountTransactionMin) {
        this.amountTransactionMin = amountTransactionMin;
    }

    public String getPriceCode() {
        return priceCode;
    }

    public TransactionPrice priceCode(String priceCode) {
        this.priceCode = priceCode;
        return this;
    }

    public void setPriceCode(String priceCode) {
        this.priceCode = priceCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public TransactionPrice serviceCode(String serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getDescription() {
        return description;
    }

    public TransactionPrice description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public TransactionPrice modificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public TransactionPrice transaction(Transaction transaction) {
        this.transaction = transaction;
        return this;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TransactionPrice)) {
            return false;
        }
        return id != null && id.equals(((TransactionPrice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransactionPrice{" +
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
            "}";
    }
}
