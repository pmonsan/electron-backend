package com.electron.mfs.pg.transactions.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TransactionInfo.
 */
@Entity
@Table(name = "t_transaction_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransactionInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 5)
    @Column(name = "transaction_property_code", length = 5, nullable = false)
    private String transactionPropertyCode;

    @NotNull
    @Size(max = 100)
    @Column(name = "value", length = 100, nullable = false)
    private String value;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("transactionInfos")
    private Transaction transaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionPropertyCode() {
        return transactionPropertyCode;
    }

    public TransactionInfo transactionPropertyCode(String transactionPropertyCode) {
        this.transactionPropertyCode = transactionPropertyCode;
        return this;
    }

    public void setTransactionPropertyCode(String transactionPropertyCode) {
        this.transactionPropertyCode = transactionPropertyCode;
    }

    public String getValue() {
        return value;
    }

    public TransactionInfo value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean isActive() {
        return active;
    }

    public TransactionInfo active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public TransactionInfo transaction(Transaction transaction) {
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
        if (!(o instanceof TransactionInfo)) {
            return false;
        }
        return id != null && id.equals(((TransactionInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransactionInfo{" +
            "id=" + getId() +
            ", transactionPropertyCode='" + getTransactionPropertyCode() + "'" +
            ", value='" + getValue() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
