package com.electron.mfs.pg.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.PgService} entity.
 */
public class PgServiceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String code;

    @NotNull
    @Size(max = 100)
    private String name;

    @NotNull
    private Boolean isNative;

    @NotNull
    private Boolean isSourceInternal;

    @NotNull
    private Boolean isDestinationInternal;

    @NotNull
    private Boolean needSubscription;

    @NotNull
    @Size(max = 5)
    private String currencyCode;

    @NotNull
    private Boolean useTransactionType;

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
    private String pgTransactionType1Code;

    @NotNull
    @Size(max = 5)
    private String pgTransactionType2Code;

    @Size(max = 5)
    private String partnerOwnerCode;

    @NotNull
    @Size(max = 5)
    private String transactionTypeCode;

    @NotNull
    @Size(max = 5)
    private String serviceAuthenticationCode;

    @Size(max = 255)
    private String contractPath;

    @Size(max = 255)
    private String description;

    private String logic;

    @NotNull
    private Boolean active;


    private Long sourceConnectorId;

    private Long destinationConnectorId;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsNative() {
        return isNative;
    }

    public void setIsNative(Boolean isNative) {
        this.isNative = isNative;
    }

    public Boolean isIsSourceInternal() {
        return isSourceInternal;
    }

    public void setIsSourceInternal(Boolean isSourceInternal) {
        this.isSourceInternal = isSourceInternal;
    }

    public Boolean isIsDestinationInternal() {
        return isDestinationInternal;
    }

    public void setIsDestinationInternal(Boolean isDestinationInternal) {
        this.isDestinationInternal = isDestinationInternal;
    }

    public Boolean isNeedSubscription() {
        return needSubscription;
    }

    public void setNeedSubscription(Boolean needSubscription) {
        this.needSubscription = needSubscription;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Boolean isUseTransactionType() {
        return useTransactionType;
    }

    public void setUseTransactionType(Boolean useTransactionType) {
        this.useTransactionType = useTransactionType;
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

    public String getPgTransactionType1Code() {
        return pgTransactionType1Code;
    }

    public void setPgTransactionType1Code(String pgTransactionType1Code) {
        this.pgTransactionType1Code = pgTransactionType1Code;
    }

    public String getPgTransactionType2Code() {
        return pgTransactionType2Code;
    }

    public void setPgTransactionType2Code(String pgTransactionType2Code) {
        this.pgTransactionType2Code = pgTransactionType2Code;
    }

    public String getPartnerOwnerCode() {
        return partnerOwnerCode;
    }

    public void setPartnerOwnerCode(String partnerOwnerCode) {
        this.partnerOwnerCode = partnerOwnerCode;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public String getServiceAuthenticationCode() {
        return serviceAuthenticationCode;
    }

    public void setServiceAuthenticationCode(String serviceAuthenticationCode) {
        this.serviceAuthenticationCode = serviceAuthenticationCode;
    }

    public String getContractPath() {
        return contractPath;
    }

    public void setContractPath(String contractPath) {
        this.contractPath = contractPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getSourceConnectorId() {
        return sourceConnectorId;
    }

    public void setSourceConnectorId(Long connectorId) {
        this.sourceConnectorId = connectorId;
    }

    public Long getDestinationConnectorId() {
        return destinationConnectorId;
    }

    public void setDestinationConnectorId(Long connectorId) {
        this.destinationConnectorId = connectorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PgServiceDTO pgServiceDTO = (PgServiceDTO) o;
        if (pgServiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pgServiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PgServiceDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", isNative='" + isIsNative() + "'" +
            ", isSourceInternal='" + isIsSourceInternal() + "'" +
            ", isDestinationInternal='" + isIsDestinationInternal() + "'" +
            ", needSubscription='" + isNeedSubscription() + "'" +
            ", currencyCode='" + getCurrencyCode() + "'" +
            ", useTransactionType='" + isUseTransactionType() + "'" +
            ", checkSubscription='" + isCheckSubscription() + "'" +
            ", ignoreFees='" + isIgnoreFees() + "'" +
            ", ignoreLimit='" + isIgnoreLimit() + "'" +
            ", ignoreCommission='" + isIgnoreCommission() + "'" +
            ", checkOtp='" + isCheckOtp() + "'" +
            ", pgTransactionType1Code='" + getPgTransactionType1Code() + "'" +
            ", pgTransactionType2Code='" + getPgTransactionType2Code() + "'" +
            ", partnerOwnerCode='" + getPartnerOwnerCode() + "'" +
            ", transactionTypeCode='" + getTransactionTypeCode() + "'" +
            ", serviceAuthenticationCode='" + getServiceAuthenticationCode() + "'" +
            ", contractPath='" + getContractPath() + "'" +
            ", description='" + getDescription() + "'" +
            ", logic='" + getLogic() + "'" +
            ", active='" + isActive() + "'" +
            ", sourceConnector=" + getSourceConnectorId() +
            ", destinationConnector=" + getDestinationConnectorId() +
            "}";
    }
}
