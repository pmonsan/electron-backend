package com.electron.mfs.pg.mdm.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.mdm.domain.TransactionType} entity.
 */
public class TransactionTypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String code;

    @NotNull
    @Size(max = 100)
    private String label;

    @NotNull
    private Boolean useTransactionGroup;

    @NotNull
    private Boolean checkSubscription;

    @NotNull
    private Boolean ignoreFees;

    @NotNull
    private Boolean ignoreLimit;

    @NotNull
    private Boolean ignoreCommission;

    @NotNull
    private Boolean checkOtp;

    @NotNull
    @Size(max = 5)
    private String pgMessageModelCode;

    @NotNull
    @Size(max = 5)
    private String transactionGroupCode;

    @NotNull
    private Boolean active;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean isUseTransactionGroup() {
        return useTransactionGroup;
    }

    public void setUseTransactionGroup(Boolean useTransactionGroup) {
        this.useTransactionGroup = useTransactionGroup;
    }

    public Boolean isCheckSubscription() {
        return checkSubscription;
    }

    public void setCheckSubscription(Boolean checkSubscription) {
        this.checkSubscription = checkSubscription;
    }

    public Boolean isIgnoreFees() {
        return ignoreFees;
    }

    public void setIgnoreFees(Boolean ignoreFees) {
        this.ignoreFees = ignoreFees;
    }

    public Boolean isIgnoreLimit() {
        return ignoreLimit;
    }

    public void setIgnoreLimit(Boolean ignoreLimit) {
        this.ignoreLimit = ignoreLimit;
    }

    public Boolean isIgnoreCommission() {
        return ignoreCommission;
    }

    public void setIgnoreCommission(Boolean ignoreCommission) {
        this.ignoreCommission = ignoreCommission;
    }

    public Boolean isCheckOtp() {
        return checkOtp;
    }

    public void setCheckOtp(Boolean checkOtp) {
        this.checkOtp = checkOtp;
    }

    public String getPgMessageModelCode() {
        return pgMessageModelCode;
    }

    public void setPgMessageModelCode(String pgMessageModelCode) {
        this.pgMessageModelCode = pgMessageModelCode;
    }

    public String getTransactionGroupCode() {
        return transactionGroupCode;
    }

    public void setTransactionGroupCode(String transactionGroupCode) {
        this.transactionGroupCode = transactionGroupCode;
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

        TransactionTypeDTO transactionTypeDTO = (TransactionTypeDTO) o;
        if (transactionTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionTypeDTO{" +
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
