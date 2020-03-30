package com.electron.mfs.pg.gateway.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.AccountBalance} entity.
 */
public class AccountBalanceDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant situationDate;

    @DecimalMin(value = "0")
    private Double balance;

    @NotNull
    private Boolean active;


    private Long accountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getSituationDate() {
        return situationDate;
    }

    public void setSituationDate(Instant situationDate) {
        this.situationDate = situationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long pgAccountId) {
        this.accountId = pgAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountBalanceDTO accountBalanceDTO = (AccountBalanceDTO) o;
        if (accountBalanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountBalanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountBalanceDTO{" +
            "id=" + getId() +
            ", situationDate='" + getSituationDate() + "'" +
            ", balance=" + getBalance() +
            ", active='" + isActive() + "'" +
            ", account=" + getAccountId() +
            "}";
    }
}
