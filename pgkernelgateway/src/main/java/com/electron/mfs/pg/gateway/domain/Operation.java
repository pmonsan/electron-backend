package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Operation.
 */
@Entity
@Table(name = "t_operation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "number", length = 10, nullable = false)
    private String number;

    @Column(name = "amount")
    private Double amount;

    @NotNull
    @Size(max = 1)
    @Column(name = "direction", length = 1, nullable = false)
    private String direction;

    @NotNull
    @Column(name = "operation_date", nullable = false)
    private Instant operationDate;

    @NotNull
    @Size(max = 50)
    @Column(name = "account_number", length = 50, nullable = false)
    private String accountNumber;

    @NotNull
    @Size(max = 5)
    @Column(name = "operation_status_code", length = 5, nullable = false)
    private String operationStatusCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "operation_type_code", length = 5, nullable = false)
    private String operationTypeCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "currency_code", length = 5, nullable = false)
    private String currencyCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "user_code", length = 5, nullable = false)
    private String userCode;

    @NotNull
    @Size(max = 255)
    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("operations")
    private Transaction transaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public Operation number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getAmount() {
        return amount;
    }

    public Operation amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDirection() {
        return direction;
    }

    public Operation direction(String direction) {
        this.direction = direction;
        return this;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Instant getOperationDate() {
        return operationDate;
    }

    public Operation operationDate(Instant operationDate) {
        this.operationDate = operationDate;
        return this;
    }

    public void setOperationDate(Instant operationDate) {
        this.operationDate = operationDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Operation accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOperationStatusCode() {
        return operationStatusCode;
    }

    public Operation operationStatusCode(String operationStatusCode) {
        this.operationStatusCode = operationStatusCode;
        return this;
    }

    public void setOperationStatusCode(String operationStatusCode) {
        this.operationStatusCode = operationStatusCode;
    }

    public String getOperationTypeCode() {
        return operationTypeCode;
    }

    public Operation operationTypeCode(String operationTypeCode) {
        this.operationTypeCode = operationTypeCode;
        return this;
    }

    public void setOperationTypeCode(String operationTypeCode) {
        this.operationTypeCode = operationTypeCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public Operation currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public Operation userCode(String userCode) {
        this.userCode = userCode;
        return this;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDescription() {
        return description;
    }

    public Operation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public Operation active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Operation transaction(Transaction transaction) {
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
        if (!(o instanceof Operation)) {
            return false;
        }
        return id != null && id.equals(((Operation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Operation{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", amount=" + getAmount() +
            ", direction='" + getDirection() + "'" +
            ", operationDate='" + getOperationDate() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", operationStatusCode='" + getOperationStatusCode() + "'" +
            ", operationTypeCode='" + getOperationTypeCode() + "'" +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", userCode='" + getUserCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
