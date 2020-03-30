package com.electron.mfs.pg.customer.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.customer.domain.Meansofpayment} entity.
 */
public class MeansofpaymentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String number;

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

    private String momoAccount;

    @NotNull
    @Size(max = 5)
    private String meansofpaymentTypeCode;

    @NotNull
    @Size(max = 5)
    private String issuerCode;

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

    public String getMomoAccount() {
        return momoAccount;
    }

    public void setMomoAccount(String momoAccount) {
        this.momoAccount = momoAccount;
    }

    public String getMeansofpaymentTypeCode() {
        return meansofpaymentTypeCode;
    }

    public void setMeansofpaymentTypeCode(String meansofpaymentTypeCode) {
        this.meansofpaymentTypeCode = meansofpaymentTypeCode;
    }

    public String getIssuerCode() {
        return issuerCode;
    }

    public void setIssuerCode(String issuerCode) {
        this.issuerCode = issuerCode;
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

        MeansofpaymentDTO meansofpaymentDTO = (MeansofpaymentDTO) o;
        if (meansofpaymentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), meansofpaymentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MeansofpaymentDTO{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", aliasAccount='" + getAliasAccount() + "'" +
            ", baccBankCode='" + getBaccBankCode() + "'" +
            ", baccBranchCode='" + getBaccBranchCode() + "'" +
            ", baccAccountNumber='" + getBaccAccountNumber() + "'" +
            ", baccRibKey='" + getBaccRibKey() + "'" +
            ", cardCvv2='" + getCardCvv2() + "'" +
            ", cardPan='" + getCardPan() + "'" +
            ", cardValidityDate='" + getCardValidityDate() + "'" +
            ", momoAccount='" + getMomoAccount() + "'" +
            ", meansofpaymentTypeCode='" + getMeansofpaymentTypeCode() + "'" +
            ", issuerCode='" + getIssuerCode() + "'" +
            ", active='" + isActive() + "'" +
            ", customer=" + getCustomerId() +
            "}";
    }
}
