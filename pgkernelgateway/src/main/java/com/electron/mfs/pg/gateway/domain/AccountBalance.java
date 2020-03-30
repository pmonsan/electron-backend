package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AccountBalance.
 */
@Entity
@Table(name = "t_account_balance")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AccountBalance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "situation_date", nullable = false)
    private Instant situationDate;

    @DecimalMin(value = "0")
    @Column(name = "balance")
    private Double balance;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("accountBalances")
    private PgAccount account;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getSituationDate() {
        return situationDate;
    }

    public AccountBalance situationDate(Instant situationDate) {
        this.situationDate = situationDate;
        return this;
    }

    public void setSituationDate(Instant situationDate) {
        this.situationDate = situationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public AccountBalance balance(Double balance) {
        this.balance = balance;
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean isActive() {
        return active;
    }

    public AccountBalance active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public PgAccount getAccount() {
        return account;
    }

    public AccountBalance account(PgAccount pgAccount) {
        this.account = pgAccount;
        return this;
    }

    public void setAccount(PgAccount pgAccount) {
        this.account = pgAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountBalance)) {
            return false;
        }
        return id != null && id.equals(((AccountBalance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AccountBalance{" +
            "id=" + getId() +
            ", situationDate='" + getSituationDate() + "'" +
            ", balance=" + getBalance() +
            ", active='" + isActive() + "'" +
            "}";
    }
}
