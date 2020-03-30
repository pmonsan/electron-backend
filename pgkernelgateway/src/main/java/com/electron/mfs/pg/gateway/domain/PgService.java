package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PgService.
 */
@Entity
@Table(name = "t_service")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PgService implements Serializable {

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
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @NotNull
    @Column(name = "is_native", nullable = false)
    private Boolean isNative;

    @NotNull
    @Column(name = "is_source_internal", nullable = false)
    private Boolean isSourceInternal;

    @NotNull
    @Column(name = "is_destination_internal", nullable = false)
    private Boolean isDestinationInternal;

    @NotNull
    @Column(name = "need_subscription", nullable = false)
    private Boolean needSubscription;

    @NotNull
    @Size(max = 5)
    @Column(name = "currency_code", length = 5, nullable = false)
    private String currencyCode;

    @NotNull
    @Column(name = "use_transaction_type", nullable = false)
    private Boolean useTransactionType;

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
    @Column(name = "pg_transaction_type_1_code", length = 5, nullable = false)
    private String pgTransactionType1Code;

    @NotNull
    @Size(max = 5)
    @Column(name = "pg_transaction_type_2_code", length = 5, nullable = false)
    private String pgTransactionType2Code;

    @Size(max = 5)
    @Column(name = "partner_owner_code", length = 5)
    private String partnerOwnerCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "transaction_type_code", length = 5, nullable = false)
    private String transactionTypeCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "service_authentication_code", length = 5, nullable = false)
    private String serviceAuthenticationCode;

    @Size(max = 255)
    @Column(name = "contract_path", length = 255)
    private String contractPath;

    @Size(max = 255)
    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "logic")
    private String logic;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("pgServices")
    private Connector sourceConnector;

    @ManyToOne
    @JsonIgnoreProperties("pgServices")
    private Connector destinationConnector;

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

    public PgService code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public PgService name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isIsNative() {
        return isNative;
    }

    public PgService isNative(Boolean isNative) {
        this.isNative = isNative;
        return this;
    }

    public void setIsNative(Boolean isNative) {
        this.isNative = isNative;
    }

    public Boolean isIsSourceInternal() {
        return isSourceInternal;
    }

    public PgService isSourceInternal(Boolean isSourceInternal) {
        this.isSourceInternal = isSourceInternal;
        return this;
    }

    public void setIsSourceInternal(Boolean isSourceInternal) {
        this.isSourceInternal = isSourceInternal;
    }

    public Boolean isIsDestinationInternal() {
        return isDestinationInternal;
    }

    public PgService isDestinationInternal(Boolean isDestinationInternal) {
        this.isDestinationInternal = isDestinationInternal;
        return this;
    }

    public void setIsDestinationInternal(Boolean isDestinationInternal) {
        this.isDestinationInternal = isDestinationInternal;
    }

    public Boolean isNeedSubscription() {
        return needSubscription;
    }

    public PgService needSubscription(Boolean needSubscription) {
        this.needSubscription = needSubscription;
        return this;
    }

    public void setNeedSubscription(Boolean needSubscription) {
        this.needSubscription = needSubscription;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public PgService currencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Boolean isUseTransactionType() {
        return useTransactionType;
    }

    public PgService useTransactionType(Boolean useTransactionType) {
        this.useTransactionType = useTransactionType;
        return this;
    }

    public void setUseTransactionType(Boolean useTransactionType) {
        this.useTransactionType = useTransactionType;
    }

    public Boolean isCheckSubscription() {
        return checkSubscription;
    }

    public PgService checkSubscription(Boolean checkSubscription) {
        this.checkSubscription = checkSubscription;
        return this;
    }

    public void setCheckSubscription(Boolean checkSubscription) {
        this.checkSubscription = checkSubscription;
    }

    public Boolean isIgnoreFees() {
        return ignoreFees;
    }

    public PgService ignoreFees(Boolean ignoreFees) {
        this.ignoreFees = ignoreFees;
        return this;
    }

    public void setIgnoreFees(Boolean ignoreFees) {
        this.ignoreFees = ignoreFees;
    }

    public Boolean isIgnoreLimit() {
        return ignoreLimit;
    }

    public PgService ignoreLimit(Boolean ignoreLimit) {
        this.ignoreLimit = ignoreLimit;
        return this;
    }

    public void setIgnoreLimit(Boolean ignoreLimit) {
        this.ignoreLimit = ignoreLimit;
    }

    public Boolean isIgnoreCommission() {
        return ignoreCommission;
    }

    public PgService ignoreCommission(Boolean ignoreCommission) {
        this.ignoreCommission = ignoreCommission;
        return this;
    }

    public void setIgnoreCommission(Boolean ignoreCommission) {
        this.ignoreCommission = ignoreCommission;
    }

    public Boolean isCheckOtp() {
        return checkOtp;
    }

    public PgService checkOtp(Boolean checkOtp) {
        this.checkOtp = checkOtp;
        return this;
    }

    public void setCheckOtp(Boolean checkOtp) {
        this.checkOtp = checkOtp;
    }

    public String getPgTransactionType1Code() {
        return pgTransactionType1Code;
    }

    public PgService pgTransactionType1Code(String pgTransactionType1Code) {
        this.pgTransactionType1Code = pgTransactionType1Code;
        return this;
    }

    public void setPgTransactionType1Code(String pgTransactionType1Code) {
        this.pgTransactionType1Code = pgTransactionType1Code;
    }

    public String getPgTransactionType2Code() {
        return pgTransactionType2Code;
    }

    public PgService pgTransactionType2Code(String pgTransactionType2Code) {
        this.pgTransactionType2Code = pgTransactionType2Code;
        return this;
    }

    public void setPgTransactionType2Code(String pgTransactionType2Code) {
        this.pgTransactionType2Code = pgTransactionType2Code;
    }

    public String getPartnerOwnerCode() {
        return partnerOwnerCode;
    }

    public PgService partnerOwnerCode(String partnerOwnerCode) {
        this.partnerOwnerCode = partnerOwnerCode;
        return this;
    }

    public void setPartnerOwnerCode(String partnerOwnerCode) {
        this.partnerOwnerCode = partnerOwnerCode;
    }

    public String getTransactionTypeCode() {
        return transactionTypeCode;
    }

    public PgService transactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
        return this;
    }

    public void setTransactionTypeCode(String transactionTypeCode) {
        this.transactionTypeCode = transactionTypeCode;
    }

    public String getServiceAuthenticationCode() {
        return serviceAuthenticationCode;
    }

    public PgService serviceAuthenticationCode(String serviceAuthenticationCode) {
        this.serviceAuthenticationCode = serviceAuthenticationCode;
        return this;
    }

    public void setServiceAuthenticationCode(String serviceAuthenticationCode) {
        this.serviceAuthenticationCode = serviceAuthenticationCode;
    }

    public String getContractPath() {
        return contractPath;
    }

    public PgService contractPath(String contractPath) {
        this.contractPath = contractPath;
        return this;
    }

    public void setContractPath(String contractPath) {
        this.contractPath = contractPath;
    }

    public String getDescription() {
        return description;
    }

    public PgService description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogic() {
        return logic;
    }

    public PgService logic(String logic) {
        this.logic = logic;
        return this;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public Boolean isActive() {
        return active;
    }

    public PgService active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Connector getSourceConnector() {
        return sourceConnector;
    }

    public PgService sourceConnector(Connector connector) {
        this.sourceConnector = connector;
        return this;
    }

    public void setSourceConnector(Connector connector) {
        this.sourceConnector = connector;
    }

    public Connector getDestinationConnector() {
        return destinationConnector;
    }

    public PgService destinationConnector(Connector connector) {
        this.destinationConnector = connector;
        return this;
    }

    public void setDestinationConnector(Connector connector) {
        this.destinationConnector = connector;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PgService)) {
            return false;
        }
        return id != null && id.equals(((PgService) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PgService{" +
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
            "}";
    }
}
