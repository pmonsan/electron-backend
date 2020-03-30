package com.electron.mfs.pg.mdm.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A TransactionType.
 */
@Entity
@Table(name = "t_transaction_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransactionType implements Serializable {

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
    @Column(name = "use_transaction_group", nullable = false)
    private Boolean useTransactionGroup;

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
    @Size(max = 5)
    @Column(name = "pg_message_model_code", length = 5, nullable = false)
    private String pgMessageModelCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "transaction_group_code", length = 5, nullable = false)
    private String transactionGroupCode;

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

    public TransactionType code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public TransactionType label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isUseTransactionGroup() {
        return useTransactionGroup;
    }

    public TransactionType useTransactionGroup(Boolean useTransactionGroup) {
        this.useTransactionGroup = useTransactionGroup;
        return this;
    }

    public void setUseTransactionGroup(Boolean useTransactionGroup) {
        this.useTransactionGroup = useTransactionGroup;
    }

    public Boolean isCheckSubscription() {
        return checkSubscription;
    }

    public TransactionType checkSubscription(Boolean checkSubscription) {
        this.checkSubscription = checkSubscription;
        return this;
    }

    public void setCheckSubscription(Boolean checkSubscription) {
        this.checkSubscription = checkSubscription;
    }

    public Boolean isIgnoreFees() {
        return ignoreFees;
    }

    public TransactionType ignoreFees(Boolean ignoreFees) {
        this.ignoreFees = ignoreFees;
        return this;
    }

    public void setIgnoreFees(Boolean ignoreFees) {
        this.ignoreFees = ignoreFees;
    }

    public Boolean isIgnoreLimit() {
        return ignoreLimit;
    }

    public TransactionType ignoreLimit(Boolean ignoreLimit) {
        this.ignoreLimit = ignoreLimit;
        return this;
    }

    public void setIgnoreLimit(Boolean ignoreLimit) {
        this.ignoreLimit = ignoreLimit;
    }

    public Boolean isIgnoreCommission() {
        return ignoreCommission;
    }

    public TransactionType ignoreCommission(Boolean ignoreCommission) {
        this.ignoreCommission = ignoreCommission;
        return this;
    }

    public void setIgnoreCommission(Boolean ignoreCommission) {
        this.ignoreCommission = ignoreCommission;
    }

    public Boolean isCheckOtp() {
        return checkOtp;
    }

    public TransactionType checkOtp(Boolean checkOtp) {
        this.checkOtp = checkOtp;
        return this;
    }

    public void setCheckOtp(Boolean checkOtp) {
        this.checkOtp = checkOtp;
    }

    public String getPgMessageModelCode() {
        return pgMessageModelCode;
    }

    public TransactionType pgMessageModelCode(String pgMessageModelCode) {
        this.pgMessageModelCode = pgMessageModelCode;
        return this;
    }

    public void setPgMessageModelCode(String pgMessageModelCode) {
        this.pgMessageModelCode = pgMessageModelCode;
    }

    public String getTransactionGroupCode() {
        return transactionGroupCode;
    }

    public TransactionType transactionGroupCode(String transactionGroupCode) {
        this.transactionGroupCode = transactionGroupCode;
        return this;
    }

    public void setTransactionGroupCode(String transactionGroupCode) {
        this.transactionGroupCode = transactionGroupCode;
    }

    public Boolean isActive() {
        return active;
    }

    public TransactionType active(Boolean active) {
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
        if (!(o instanceof TransactionType)) {
            return false;
        }
        return id != null && id.equals(((TransactionType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TransactionType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", useTransactionGroup='" + isUseTransactionGroup() + "'" +
            ", checkSubscription='" + isCheckSubscription() + "'" +
            ", ignoreFees='" + isIgnoreFees() + "'" +
            ", ignoreLimit='" + isIgnoreLimit() + "'" +
            ", ignoreCommission='" + isIgnoreCommission() + "'" +
            ", checkOtp='" + isCheckOtp() + "'" +
            ", pgMessageModelCode='" + getPgMessageModelCode() + "'" +
            ", transactionGroupCode='" + getTransactionGroupCode() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
