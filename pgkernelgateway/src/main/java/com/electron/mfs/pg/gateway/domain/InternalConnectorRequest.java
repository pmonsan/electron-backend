package com.electron.mfs.pg.gateway.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A InternalConnectorRequest.
 */
@Entity
@Table(name = "t_internal_connector_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InternalConnectorRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "number", length = 50, nullable = false)
    private String number;

    @NotNull
    @Size(max = 10)
    @Column(name = "module", length = 10, nullable = false)
    private String module;

    @NotNull
    @Size(max = 20)
    @Column(name = "connector", length = 20, nullable = false)
    private String connector;

    @NotNull
    @Size(max = 20)
    @Column(name = "connector_type", length = 20, nullable = false)
    private String connectorType;

    @Size(max = 255)
    @Column(name = "request_data", length = 255)
    private String requestData;

    @NotNull
    @Column(name = "registration_date", nullable = false)
    private Instant registrationDate;

    @Size(max = 50)
    @Column(name = "pgaps_transaction_number", length = 50)
    private String pgapsTransactionNumber;

    @Size(max = 50)
    @Column(name = "service_id", length = 50)
    private String serviceId;

    @Size(max = 50)
    @Column(name = "account_number", length = 50)
    private String accountNumber;

    @DecimalMin(value = "0")
    @Column(name = "amount")
    private Double amount;

    @DecimalMin(value = "0")
    @Column(name = "balance")
    private Double balance;

    @Column(name = "account_validation")
    private Boolean accountValidation;

    @Column(name = "number_of_transactions")
    private Integer numberOfTransactions;

    @Column(name = "last_transactions")
    private String lastTransactions;

    @Size(max = 50)
    @Column(name = "action", length = 50)
    private String action;

    @Column(name = "response_date")
    private Instant responseDate;

    @Size(max = 100)
    @Column(name = "status", length = 100)
    private String status;

    @Size(max = 100)
    @Column(name = "reason", length = 100)
    private String reason;

    @Size(max = 50)
    @Column(name = "partner_transaction_number", length = 50)
    private String partnerTransactionNumber;

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

    public String getNumber() {
        return number;
    }

    public InternalConnectorRequest number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getModule() {
        return module;
    }

    public InternalConnectorRequest module(String module) {
        this.module = module;
        return this;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getConnector() {
        return connector;
    }

    public InternalConnectorRequest connector(String connector) {
        this.connector = connector;
        return this;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }

    public String getConnectorType() {
        return connectorType;
    }

    public InternalConnectorRequest connectorType(String connectorType) {
        this.connectorType = connectorType;
        return this;
    }

    public void setConnectorType(String connectorType) {
        this.connectorType = connectorType;
    }

    public String getRequestData() {
        return requestData;
    }

    public InternalConnectorRequest requestData(String requestData) {
        this.requestData = requestData;
        return this;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public InternalConnectorRequest registrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getPgapsTransactionNumber() {
        return pgapsTransactionNumber;
    }

    public InternalConnectorRequest pgapsTransactionNumber(String pgapsTransactionNumber) {
        this.pgapsTransactionNumber = pgapsTransactionNumber;
        return this;
    }

    public void setPgapsTransactionNumber(String pgapsTransactionNumber) {
        this.pgapsTransactionNumber = pgapsTransactionNumber;
    }

    public String getServiceId() {
        return serviceId;
    }

    public InternalConnectorRequest serviceId(String serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public InternalConnectorRequest accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public InternalConnectorRequest amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBalance() {
        return balance;
    }

    public InternalConnectorRequest balance(Double balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean isAccountValidation() {
        return accountValidation;
    }

    public InternalConnectorRequest accountValidation(Boolean accountValidation) {
        this.accountValidation = accountValidation;
        return this;
    }

    public void setAccountValidation(Boolean accountValidation) {
        this.accountValidation = accountValidation;
    }

    public Integer getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public InternalConnectorRequest numberOfTransactions(Integer numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
        return this;
    }

    public void setNumberOfTransactions(Integer numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public String getLastTransactions() {
        return lastTransactions;
    }

    public InternalConnectorRequest lastTransactions(String lastTransactions) {
        this.lastTransactions = lastTransactions;
        return this;
    }

    public void setLastTransactions(String lastTransactions) {
        this.lastTransactions = lastTransactions;
    }

    public String getAction() {
        return action;
    }

    public InternalConnectorRequest action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Instant getResponseDate() {
        return responseDate;
    }

    public InternalConnectorRequest responseDate(Instant responseDate) {
        this.responseDate = responseDate;
        return this;
    }

    public void setResponseDate(Instant responseDate) {
        this.responseDate = responseDate;
    }

    public String getStatus() {
        return status;
    }

    public InternalConnectorRequest status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public InternalConnectorRequest reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPartnerTransactionNumber() {
        return partnerTransactionNumber;
    }

    public InternalConnectorRequest partnerTransactionNumber(String partnerTransactionNumber) {
        this.partnerTransactionNumber = partnerTransactionNumber;
        return this;
    }

    public void setPartnerTransactionNumber(String partnerTransactionNumber) {
        this.partnerTransactionNumber = partnerTransactionNumber;
    }

    public Boolean isActive() {
        return active;
    }

    public InternalConnectorRequest active(Boolean active) {
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
        if (!(o instanceof InternalConnectorRequest)) {
            return false;
        }
        return id != null && id.equals(((InternalConnectorRequest) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InternalConnectorRequest{" +
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
