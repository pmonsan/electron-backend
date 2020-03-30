package com.electron.mfs.pg.pg8583client.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.pg8583client.domain.Pg8583Request} entity.
 */
public class Pg8583RequestDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String number;

    @NotNull
    @Size(max = 100)
    private String apiKey;

    @NotNull
    @Size(max = 255)
    private String encryptedData;

    @Size(max = 255)
    private String decryptedData;

    @NotNull
    private Instant registrationDate;

    private Instant responseDate;

    @Size(max = 255)
    private String requestResponse;

    @Size(max = 100)
    private String status;

    @Size(max = 100)
    private String reason;

    @Size(max = 255)
    private String pgapsMessage;

    @Size(max = 50)
    private String pgapsTransactionNumber;

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

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getDecryptedData() {
        return decryptedData;
    }

    public void setDecryptedData(String decryptedData) {
        this.decryptedData = decryptedData;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Instant getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Instant responseDate) {
        this.responseDate = responseDate;
    }

    public String getRequestResponse() {
        return requestResponse;
    }

    public void setRequestResponse(String requestResponse) {
        this.requestResponse = requestResponse;
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

    public String getPgapsMessage() {
        return pgapsMessage;
    }

    public void setPgapsMessage(String pgapsMessage) {
        this.pgapsMessage = pgapsMessage;
    }

    public String getPgapsTransactionNumber() {
        return pgapsTransactionNumber;
    }

    public void setPgapsTransactionNumber(String pgapsTransactionNumber) {
        this.pgapsTransactionNumber = pgapsTransactionNumber;
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

        Pg8583RequestDTO pg8583RequestDTO = (Pg8583RequestDTO) o;
        if (pg8583RequestDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pg8583RequestDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pg8583RequestDTO{" +
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
