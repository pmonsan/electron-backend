package com.electron.mfs.pg.pg8583.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PgChannelAuthorized.
 */
@Entity
@Table(name = "t_pg_channel_authorized")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PgChannelAuthorized implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 5)
    @Column(name = "transaction_type_code", length = 5, nullable = false)
    private String transactionTypeCode;

    @NotNull
    @Column(name = "registration_date", nullable = false)
    private Instant registrationDate;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("pgChannelAuthorizeds")
    private PgChannel pgChannel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public PgChannelAuthorized transactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
        return this;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public PgChannelAuthorized registrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
        return this;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Boolean isActive() {
        return active;
    }

    public PgChannelAuthorized active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public PgChannel getPgChannel() {
        return pgChannel;
    }

    public PgChannelAuthorized pgChannel(PgChannel pgChannel) {
        this.pgChannel = pgChannel;
        return this;
    }

    public void setPgChannel(PgChannel pgChannel) {
        this.pgChannel = pgChannel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PgChannelAuthorized)) {
            return false;
        }
        return id != null && id.equals(((PgChannelAuthorized) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PgChannelAuthorized{" +
            "id=" + getId() +
            ", transactionTypeCode='" + getTransactionTypeCode() + "'" +
            ", registrationDate='" + getRegistrationDate() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
