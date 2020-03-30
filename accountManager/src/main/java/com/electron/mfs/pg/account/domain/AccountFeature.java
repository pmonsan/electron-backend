package com.electron.mfs.pg.account.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A AccountFeature.
 */
@Entity
@Table(name = "t_account_feature")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AccountFeature implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "activation_date", nullable = false)
    private Instant activationDate;

    @NotNull
    @Size(max = 5)
    @Column(name = "feature_code", length = 5, nullable = false)
    private String featureCode;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("accountFeatures")
    private PgAccount account;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getActivationDate() {
        return activationDate;
    }

    public AccountFeature activationDate(Instant activationDate) {
        this.activationDate = activationDate;
        return this;
    }

    public void setActivationDate(Instant activationDate) {
        this.activationDate = activationDate;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public AccountFeature featureCode(String featureCode) {
        this.featureCode = featureCode;
        return this;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public Boolean isActive() {
        return active;
    }

    public AccountFeature active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public PgAccount getAccount() {
        return account;
    }

    public AccountFeature account(PgAccount pgAccount) {
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
        if (!(o instanceof AccountFeature)) {
            return false;
        }
        return id != null && id.equals(((AccountFeature) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AccountFeature{" +
            "id=" + getId() +
            ", activationDate='" + getActivationDate() + "'" +
            ", featureCode='" + getFeatureCode() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
