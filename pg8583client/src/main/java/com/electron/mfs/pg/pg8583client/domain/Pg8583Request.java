package com.electron.mfs.pg.pg8583client.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Pg8583Request.
 */
@Entity
@Table(name = "t_pg_8583_request")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pg8583Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "number", length = 50, nullable = false)
    private String number;

    @NotNull
    @Size(max = 100)
    @Column(name = "api_key", length = 100, nullable = false)
    private String apiKey;

    @NotNull
    @Size(max = 255)
    @Column(name = "encrypted_data", length = 255, nullable = false)
    private String encryptedData;

    @Size(max = 255)
    @Column(name = "decrypted_data", length = 255)
    private String decryptedData;

    @NotNull
    @Column(name = "registration_date", nullable = false)
    private Instant registrationDate;

    @Column(name = "response_date")
    private Instant responseDate;

    @Size(max = 255)
    @Column(name = "request_response", length = 255)
    private String requestResponse;

    @Size(max = 100)
    @Column(name = "status", length = 100)
    private String status;

    @Size(max = 100)
    @Column(name = "reason", length = 100)
    private String reason;

    @Size(max = 255)
    @Column(name = "pgaps_message", length = 255)
    private String pgapsMessage;

    @Size(max = 50)
    @Column(name = "pgaps_transaction_number", length = 50)
    private String pgapsTransactionNumber;

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

    public Pg8583Request number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getApiKey() {
        return apiKey;
    }

    public Pg8583Request apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public Pg8583Request encryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
        return this;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getDecryptedData() {
        return decryptedData;
    }

    public Pg8583Request decryptedData(String decryptedData) {
        this.decryptedData = decryptedData;
        return this;
    }

    public void setDecryptedData(String decryptedData) {
        this.decryptedData = decryptedData;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public Pg8583Request registrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Instant getResponseDate() {
        return responseDate;
    }

    public Pg8583Request responseDate(Instant responseDate) {
        this.responseDate = responseDate;
        return this;
    }

    public void setResponseDate(Instant responseDate) {
        this.responseDate = responseDate;
    }

    public String getRequestResponse() {
        return requestResponse;
    }

    public Pg8583Request requestResponse(String requestResponse) {
        this.requestResponse = requestResponse;
        return this;
    }

    public void setRequestResponse(String requestResponse) {
        this.requestResponse = requestResponse;
    }

    public String getStatus() {
        return status;
    }

    public Pg8583Request status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public Pg8583Request reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPgapsMessage() {
        return pgapsMessage;
    }

    public Pg8583Request pgapsMessage(String pgapsMessage) {
        this.pgapsMessage = pgapsMessage;
        return this;
    }

    public void setPgapsMessage(String pgapsMessage) {
        this.pgapsMessage = pgapsMessage;
    }

    public String getPgapsTransactionNumber() {
        return pgapsTransactionNumber;
    }

    public Pg8583Request pgapsTransactionNumber(String pgapsTransactionNumber) {
        this.pgapsTransactionNumber = pgapsTransactionNumber;
        return this;
    }

    public void setPgapsTransactionNumber(String pgapsTransactionNumber) {
        this.pgapsTransactionNumber = pgapsTransactionNumber;
    }

    public Boolean isActive() {
        return active;
    }

    public Pg8583Request active(Boolean active) {
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
        if (!(o instanceof Pg8583Request)) {
            return false;
        }
        return id != null && id.equals(((Pg8583Request) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pg8583Request{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", apiKey='" + getApiKey() + "'" +
            ", encryptedData='" + getEncryptedData() + "'" +
            ", decryptedData='" + getDecryptedData() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", responseDate='" + getResponseDate() + "'" +
            ", requestResponse='" + getRequestResponse() + "'" +
            ", status='" + getStatus() + "'" +
            ", reason='" + getReason() + "'" +
            ", pgapsMessage='" + getPgapsMessage() + "'" +
            ", pgapsTransactionNumber='" + getPgapsTransactionNumber() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
