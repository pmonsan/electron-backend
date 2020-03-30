package com.electron.mfs.pg.transactions.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Transaction.
 */
@Entity
@Table(name = "t_transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "transaction_number", length = 50, nullable = false)
    private String transactionNumber;

    @NotNull
    @Size(max = 100)
    @Column(name = "label", length = 100, nullable = false)
    private String label;

    @NotNull
    @DecimalMin(value = "0")
    @Column(name = "transaction_amount", nullable = false)
    private Double transactionAmount;

    @NotNull
    @Column(name = "transaction_date", nullable = false)
    private Instant transactionDate;

    @NotNull
    @Column(name = "fees_supported", nullable = false)
    private Boolean feesSupported;

    @DecimalMin(value = "0")
    @Column(name = "transaction_fees")
    private Double transactionFees;

    @NotNull
    @Size(max = 10)
    @Column(name = "price_code", length = 10, nullable = false)
    private String priceCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "from_partner_code", length = 5, nullable = false)
    private String fromPartnerCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "to_partner_code", length = 5, nullable = false)
    private String toPartnerCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "transaction_status_code", length = 5, nullable = false)
    private String transactionStatusCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "transaction_type_code", length = 5, nullable = false)
    private String transactionTypeCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "service_code", length = 5, nullable = false)
    private String serviceCode;

    @Size(max = 255)
    @Column(name = "comment", length = 255)
    private String comment;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public Transaction transactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
        return this;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getLabel() {
        return label;
    }

    public Transaction label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public Transaction transactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Instant getTransactionDate() {
        return transactionDate;
    }

    public Transaction transactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public void setTransactionDate(Instant transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Boolean isFeesSupported() {
        return feesSupported;
    }

    public Transaction feesSupported(Boolean feesSupported) {
        this.feesSupported = feesSupported;
        return this;
    }

    public void setFeesSupported(Boolean feesSupported) {
        this.feesSupported = feesSupported;
    }

    public Double getTransactionFees() {
        return transactionFees;
    }

    public Transaction transactionFees(Double transactionFees) {
        this.transactionFees = transactionFees;
        return this;
    }

    public void setTransactionFees(Double transactionFees) {
        this.transactionFees = transactionFees;
    }

    public String getPriceCode() {
        return priceCode;
    }

    public Transaction priceCode(String priceCode) {
        this.priceCode = priceCode;
        return this;
    }

    public void setPriceCode(String priceCode) {
        this.priceCode = priceCode;
    }

    public String getFromPartnerCode() {
        return fromPartnerCode;
    }

    public Transaction fromPartnerCode(String fromPartnerCode) {
        this.fromPartnerCode = fromPartnerCode;
        return this;
    }

    public void setFromPartnerCode(String fromPartnerCode) {
        this.fromPartnerCode = fromPartnerCode;
    }

    public String getToPartnerCode() {
        return toPartnerCode;
    }

    public Transaction toPartnerCode(String toPartnerCode) {
        this.toPartnerCode = toPartnerCode;
        return this;
    }

    public void setToPartnerCode(String toPartnerCode) {
        this.toPartnerCode = toPartnerCode;
    }

    public String getTransactionStatusCode() {
        return transactionStatusCode;
    }

    public Transaction transactionStatusCode(String transactionStatusCode) {
        this.transactionStatusCode = transactionStatusCode;
        return this;
    }

    public void setTransactionStatusCode(String transactionStatusCode) {
        this.transactionStatusCode = transactionStatusCode;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public Transaction transactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
        return this;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public Transaction serviceCode(String serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getComment() {
        return comment;
    }

    public Transaction comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isActive() {
        return active;
    }

    public Transaction active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transaction{" +
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
