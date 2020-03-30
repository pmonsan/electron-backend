package com.electron.mfs.pg.transactions.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.transactions.domain.InternalConnectorRequest} entity.
 */
public class InternalConnectorRequestDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String number;

    @NotNull
    @Size(max = 10)
    private String module;

    @NotNull
    @Size(max = 20)
    private String connector;

    @NotNull
    @Size(max = 20)
    private String connectorType;

    @Size(max = 255)
    private String requestData;

    @NotNull
    private Instant registrationDate;

    @Size(max = 50)
    private String pgapsTransactionNumber;

    @Size(max = 50)
    private String serviceId;

    @Size(max = 50)
    private String accountNumber;

    @DecimalMin(value = "0")
    private Double amount;

    @DecimalMin(value = "0")
    private Double balance;

    private Boolean accountValidation;

    private Integer numberOfTransactions;

    private String lastTransactions;

    @Size(max = 50)
    private String action;

    private Instant responseDate;

    @Size(max = 100)
    private String status;

    @Size(max = 100)
    private String reason;

    @Size(max = 50)
    private String partnerTransactionNumber;

    @NotNull
    private Boolean active;


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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPgapsTransactionNumber() {
        return pgapsTransactionNumber;
    }

    public void setPgapsTransactionNumber(String pgapsTransactionNumber) {
        this.pgapsTransactionNumber = pgapsTransactionNumber;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean isAccountValidation() {
        return accountValidation;
    }

    public void setAccountValidation(Boolean accountValidation) {
        this.accountValidation = accountValidation;
    }

    public Integer getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(Integer numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public String getLastTransactions() {
        return lastTransactions;
    }

    public void setLastTransactions(String lastTransactions) {
        this.lastTransactions = lastTransactions;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Instant getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Instant responseDate) {
        this.responseDate = responseDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPartnerTransactionNumber() {
        return partnerTransactionNumber;
    }

    public void setPartnerTransactionNumber(String partnerTransactionNumber) {
        this.partnerTransactionNumber = partnerTransactionNumber;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InternalConnectorRequestDTO internalConnectorRequestDTO = (InternalConnectorRequestDTO) o;
        if (internalConnectorRequestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), internalConnectorRequestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InternalConnectorRequestDTO{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", module='" + getModule() + "'" +
            ", connector='" + getConnector() + "'" +
            ", connectorType='" + getConnectorType() + "'" +
            ", requestData='" + getRequestData() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", pgapsTransactionNumber='" + getPgapsTransactionNumber() + "'" +
            ", serviceId='" + getServiceId() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", amount=" + getAmount() +
            ", balance=" + getBalance() +
            ", accountValidation='" + isAccountValidation() + "'" +
            ", numberOfTransactions=" + getNumberOfTransactions() +
            ", lastTransactions='" + getLastTransactions() + "'" +
            ", action='" + getAction() + "'" +
            ", responseDate='" + getResponseDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", reason='" + getReason() + "'" +
            ", partnerTransactionNumber='" + getPartnerTransactionNumber() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
