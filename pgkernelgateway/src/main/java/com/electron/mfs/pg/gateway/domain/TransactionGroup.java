package com.electron.mfs.pg.gateway.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TransactionGroup.
 */
@Entity
@Table(name = "t_transaction_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransactionGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 5)
    @Column(name = "code", length = 5, nullable = false)
    private String code;

    @NotNull
    @Size(max = 100)
    @Column(name = "label", length = 100, nullable = false)
    private String label;

    @NotNull
    @Column(name = "check_subscription", nullable = false)
    private Boolean checkSubscription;

    @NotNull
    @Column(name = "ignore_fees", nullable = false)
    private Boolean ignoreFees;

    @NotNull
    @Column(name = "ignore_limit", nullable = false)
    private Boolean ignoreLimit;

    @NotNull
    @Column(name = "ignore_commission", nullable = false)
    private Boolean ignoreCommission;

    @NotNull
    @Column(name = "check_otp", nullable = false)
    private Boolean checkOtp;

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

    public String getCode() {
        return code;
    }

    public TransactionGroup code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public TransactionGroup label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isCheckSubscription() {
        return checkSubscription;
    }

    public TransactionGroup checkSubscription(Boolean checkSubscription) {
        this.checkSubscription = checkSubscription;
        return this;
    }

    public void setCheckSubscription(Boolean checkSubscription) {
        this.checkSubscription = checkSubscription;
    }

    public Boolean isIgnoreFees() {
        return ignoreFees;
    }

    public TransactionGroup ignoreFees(Boolean ignoreFees) {
        this.ignoreFees = ignoreFees;
        return this;
    }

    public void setIgnoreFees(Boolean ignoreFees) {
        this.ignoreFees = ignoreFees;
    }

    public Boolean isIgnoreLimit() {
        return ignoreLimit;
    }

    public TransactionGroup ignoreLimit(Boolean ignoreLimit) {
        this.ignoreLimit = ignoreLimit;
        return this;
    }

    public void setIgnoreLimit(Boolean ignoreLimit) {
        this.ignoreLimit = ignoreLimit;
    }

    public Boolean isIgnoreCommission() {
        return ignoreCommission;
    }

    public TransactionGroup ignoreCommission(Boolean ignoreCommission) {
        this.ignoreCommission = ignoreCommission;
        return this;
    }

    public void setIgnoreCommission(Boolean ignoreCommission) {
        this.ignoreCommission = ignoreCommission;
    }

    public Boolean isCheckOtp() {
        return checkOtp;
    }

    public TransactionGroup checkOtp(Boolean checkOtp) {
        this.checkOtp = checkOtp;
        return this;
    }

    public void setCheckOtp(Boolean checkOtp) {
        this.checkOtp = checkOtp;
    }

    public Boolean isActive() {
        return active;
    }

    public TransactionGroup active(Boolean active) {
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
        if (!(o instanceof TransactionGroup)) {
            return false;
        }
        return id != null && id.equals(((TransactionGroup) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransactionGroup{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", checkSubscription='" + isCheckSubscription() + "'" +
            ", ignoreFees='" + isIgnoreFees() + "'" +
            ", ignoreLimit='" + isIgnoreLimit() + "'" +
            ", ignoreCommission='" + isIgnoreCommission() + "'" +
            ", checkOtp='" + isCheckOtp() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
