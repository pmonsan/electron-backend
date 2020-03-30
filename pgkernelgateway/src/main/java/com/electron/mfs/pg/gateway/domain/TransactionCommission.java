package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TransactionCommission.
 */
@Entity
@Table(name = "t_transaction_commission")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransactionCommission implements Serializable {

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

    @NotNull
    @Column(name = "amount_in_percent", nullable = false)
    private Boolean amountInPercent;

    @NotNull
    @Column(name = "date_creation", nullable = false)
    private Instant dateCreation;

    @DecimalMin(value = "0")
    @Column(name = "percent")
    private Double percent;

    @NotNull
    @Size(max = 5)
    @Column(name = "currency_code", length = 5, nullable = false)
    private String currencyCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "partner_code", length = 5, nullable = false)
    private String partnerCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "service_code", length = 5, nullable = false)
    private String serviceCode;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @DecimalMin(value = "0")
    @Column(name = "commission")
    private Double commission;

    @ManyToOne
    @JsonIgnoreProperties("transactionCommissions")
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

    public TransactionCommission code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public TransactionCommission label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getAmount() {
        return amount;
    }

    public TransactionCommission amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean isAmountInPercent() {
        return amountInPercent;
    }

    public TransactionCommission amountInPercent(Boolean amountInPercent) {
        this.amountInPercent = amountInPercent;
        return this;
    }

    public void setAmountInPercent(Boolean amountInPercent) {
        this.amountInPercent = amountInPercent;
    }

    public Instant getDateCreation() {
        return dateCreation;
    }

    public TransactionCommission dateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
        return this;
    }

    public void setDateCreation(Instant dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Double getPercent() {
        return percent;
    }

    public TransactionCommission percent(Double percent) {
        this.percent = percent;
        return this;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public TransactionCommission currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public TransactionCommission partnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
        return this;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public TransactionCommission serviceCode(String serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getDescription() {
        return description;
    }

    public TransactionCommission description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCommission() {
        return commission;
    }

    public TransactionCommission commission(Double commission) {
        this.commission = commission;
        return this;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public TransactionCommission transaction(Transaction transaction) {
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
        if (!(o instanceof TransactionCommission)) {
            return false;
        }
        return id != null && id.equals(((TransactionCommission) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransactionCommission{" +
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
            "}";
    }
}
