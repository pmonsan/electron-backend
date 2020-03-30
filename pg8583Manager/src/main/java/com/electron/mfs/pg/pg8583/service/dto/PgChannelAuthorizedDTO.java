package com.electron.mfs.pg.pg8583.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.pg8583.domain.PgChannelAuthorized} entity.
 */
public class PgChannelAuthorizedDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String transactionTypeCode;

    @NotNull
    private Instant registrationDate;

    @NotNull
    private Boolean active;


    private Long pgChannelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getPgChannelId() {
        return pgChannelId;
    }

    public void setPgChannelId(Long pgChannelId) {
        this.pgChannelId = pgChannelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PgChannelAuthorizedDTO pgChannelAuthorizedDTO = (PgChannelAuthorizedDTO) o;
        if (pgChannelAuthorizedDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pgChannelAuthorizedDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PgChannelAuthorizedDTO{" +
            "id=" + getId() +
            ", transactionTypeCode='" + getTransactionTypeCode() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", active='" + isActive() + "'" +
            ", pgChannel=" + getPgChannelId() +
            "}";
    }
}
