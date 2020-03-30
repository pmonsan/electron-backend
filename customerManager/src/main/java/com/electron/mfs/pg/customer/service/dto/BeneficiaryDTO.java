package com.electron.mfs.pg.customer.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.customer.domain.Beneficiary} entity.
 */
public class BeneficiaryDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String number;

    @NotNull
    private Boolean isCompany;

    @Size(max = 50)
    private String firstname;

    @Size(max = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    private String aliasAccount;

    @Size(max = 10)
    private String baccBankCode;

    @Size(max = 10)
    private String baccBranchCode;

    @Size(max = 50)
    private String baccAccountNumber;

    @Size(max = 5)
    private String baccRibKey;

    @Size(max = 5)
    private String cardCvv2;

    @Size(max = 20)
    private String cardPan;

    @Size(max = 8)
    private String cardValidityDate;

    @NotNull
    private Boolean isDmAccount;

    @Size(max = 20)
    private String momoAccountNumber;

    @NotNull
    @Size(max = 5)
    private String beneficiaryRelationshipCode;

    @NotNull
    @Size(max = 5)
    private String beneficiaryTypeCode;

    @NotNull
    private Boolean active;


    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean isIsCompany() {
        return isCompany;
    }

    public void setIsCompany(Boolean isCompany) {
        this.isCompany = isCompany;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasAccount() {
        return aliasAccount;
    }

    public void setAliasAccount(String aliasAccount) {
        this.aliasAccount = aliasAccount;
    }

    public String getBaccBankCode() {
        return baccBankCode;
    }

    public void setBaccBankCode(String baccBankCode) {
        this.baccBankCode = baccBankCode;
    }

    public String getBaccBranchCode() {
        return baccBranchCode;
    }

    public void setBaccBranchCode(String baccBranchCode) {
        this.baccBranchCode = baccBranchCode;
    }

    public String getBaccAccountNumber() {
        return baccAccountNumber;
    }

    public void setBaccAccountNumber(String baccAccountNumber) {
        this.baccAccountNumber = baccAccountNumber;
    }

    public String getBaccRibKey() {
        return baccRibKey;
    }

    public void setBaccRibKey(String baccRibKey) {
        this.baccRibKey = baccRibKey;
    }

    public String getCardCvv2() {
        return cardCvv2;
    }

    public void setCardCvv2(String cardCvv2) {
        this.cardCvv2 = cardCvv2;
    }

    public String getCardPan() {
        return cardPan;
    }

    public void setCardPan(String cardPan) {
        this.cardPan = cardPan;
    }

    public String getCardValidityDate() {
        return cardValidityDate;
    }

    public void setCardValidityDate(String cardValidityDate) {
        this.cardValidityDate = cardValidityDate;
    }

    public Boolean isIsDmAccount() {
        return isDmAccount;
    }

    public void setIsDmAccount(Boolean isDmAccount) {
        this.isDmAccount = isDmAccount;
    }

    public String getMomoAccountNumber() {
        return momoAccountNumber;
    }

    public void setMomoAccountNumber(String momoAccountNumber) {
        this.momoAccountNumber = momoAccountNumber;
    }

    public String getBeneficiaryRelationshipCode() {
        return beneficiaryRelationshipCode;
    }

    public void setBeneficiaryRelationshipCode(String beneficiaryRelationshipCode) {
        this.beneficiaryRelationshipCode = beneficiaryRelationshipCode;
    }

    public String getBeneficiaryTypeCode() {
        return beneficiaryTypeCode;
    }

    public void setBeneficiaryTypeCode(String beneficiaryTypeCode) {
        this.beneficiaryTypeCode = beneficiaryTypeCode;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BeneficiaryDTO beneficiaryDTO = (BeneficiaryDTO) o;
        if (beneficiaryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), beneficiaryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BeneficiaryDTO{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", isCompany='" + isIsCompany() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", name='" + getName() + "'" +
            ", aliasAccount='" + getAliasAccount() + "'" +
            ", baccBankCode='" + getBaccBankCode() + "'" +
            ", baccBranchCode='" + getBaccBranchCode() + "'" +
            ", baccAccountNumber='" + getBaccAccountNumber() + "'" +
            ", baccRibKey='" + getBaccRibKey() + "'" +
            ", cardCvv2='" + getCardCvv2() + "'" +
            ", cardPan='" + getCardPan() + "'" +
            ", cardValidityDate='" + getCardValidityDate() + "'" +
            ", isDmAccount='" + isIsDmAccount() + "'" +
            ", momoAccountNumber='" + getMomoAccountNumber() + "'" +
            ", beneficiaryRelationshipCode='" + getBeneficiaryRelationshipCode() + "'" +
            ", beneficiaryTypeCode='" + getBeneficiaryTypeCode() + "'" +
            ", active='" + isActive() + "'" +
            ", customer=" + getCustomerId() +
            "}";
    }
}
