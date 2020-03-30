package com.electron.mfs.pg.transactions.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.transactions.domain.Operation} entity.
 */
public class OperationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String number;

    private Double amount;

    @NotNull
    @Size(max = 1)
    private String direction;

    @NotNull
    private Instant operationDate;

    @NotNull
    @Size(max = 50)
    private String accountNumber;

    @NotNull
    @Size(max = 5)
    private String operationStatusCode;

    @NotNull
    @Size(max = 5)
    private String operationTypeCode;

    @NotNull
    @Size(max = 5)
    private String currencyCode;

    @NotNull
    @Size(max = 5)
    private String userCode;

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    private Boolean active;


    private Long transactionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Instant getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Instant operationDate) {
        this.operationDate = operationDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getOperationStatusCode() {
        return operationStatusCode;
    }

    public void setOperationStatusCode(String operationStatusCode) {
        this.operationStatusCode = operationStatusCode;
    }

    public String getOperationTypeCode() {
        return operationTypeCode;
    }

    public void setOperationTypeCode(String operationTypeCode) {
        this.operationTypeCode = operationTypeCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

        OperationDTO operationDTO = (OperationDTO) o;
        if (operationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OperationDTO{" +
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
            ", transaction=" + getTransactionId() +
            "}";
    }
}
