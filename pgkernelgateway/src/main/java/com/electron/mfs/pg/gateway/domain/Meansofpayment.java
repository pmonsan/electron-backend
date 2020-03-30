package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Meansofpayment.
 */
@Entity
@Table(name = "t_meansofpayment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Meansofpayment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "number", length = 10, nullable = false)
    private String number;

    @Size(max = 50)
    @Column(name = "alias_account", length = 50)
    private String aliasAccount;

    @Size(max = 10)
    @Column(name = "bacc_bank_code", length = 10)
    private String baccBankCode;

    @Size(max = 10)
    @Column(name = "bacc_branch_code", length = 10)
    private String baccBranchCode;

    @Size(max = 50)
    @Column(name = "bacc_account_number", length = 50)
    private String baccAccountNumber;

    @Size(max = 5)
    @Column(name = "bacc_rib_key", length = 5)
    private String baccRibKey;

    @Size(max = 5)
    @Column(name = "card_cvv_2", length = 5)
    private String cardCvv2;

    @Size(max = 20)
    @Column(name = "card_pan", length = 20)
    private String cardPan;

    @Size(max = 8)
    @Column(name = "card_validity_date", length = 8)
    private String cardValidityDate;

    @Column(name = "momo_account")
    private String momoAccount;

    @NotNull
    @Size(max = 5)
    @Column(name = "meansofpayment_type_code", length = 5, nullable = false)
    private String meansofpaymentTypeCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "issuer_code", length = 5, nullable = false)
    private String issuerCode;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("meansofpayments")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public Meansofpayment number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAliasAccount() {
        return aliasAccount;
    }

    public Meansofpayment aliasAccount(String aliasAccount) {
        this.aliasAccount = aliasAccount;
        return this;
    }

    public void setAliasAccount(String aliasAccount) {
        this.aliasAccount = aliasAccount;
    }

    public String getBaccBankCode() {
        return baccBankCode;
    }

    public Meansofpayment baccBankCode(String baccBankCode) {
        this.baccBankCode = baccBankCode;
        return this;
    }

    public void setBaccBankCode(String baccBankCode) {
        this.baccBankCode = baccBankCode;
    }

    public String getBaccBranchCode() {
        return baccBranchCode;
    }

    public Meansofpayment baccBranchCode(String baccBranchCode) {
        this.baccBranchCode = baccBranchCode;
        return this;
    }

    public void setBaccBranchCode(String baccBranchCode) {
        this.baccBranchCode = baccBranchCode;
    }

    public String getBaccAccountNumber() {
        return baccAccountNumber;
    }

    public Meansofpayment baccAccountNumber(String baccAccountNumber) {
        this.baccAccountNumber = baccAccountNumber;
        return this;
    }

    public void setBaccAccountNumber(String baccAccountNumber) {
        this.baccAccountNumber = baccAccountNumber;
    }

    public String getBaccRibKey() {
        return baccRibKey;
    }

    public Meansofpayment baccRibKey(String baccRibKey) {
        this.baccRibKey = baccRibKey;
        return this;
    }

    public void setBaccRibKey(String baccRibKey) {
        this.baccRibKey = baccRibKey;
    }

    public String getCardCvv2() {
        return cardCvv2;
    }

    public Meansofpayment cardCvv2(String cardCvv2) {
        this.cardCvv2 = cardCvv2;
        return this;
    }

    public void setCardCvv2(String cardCvv2) {
        this.cardCvv2 = cardCvv2;
    }

    public String getCardPan() {
        return cardPan;
    }

    public Meansofpayment cardPan(String cardPan) {
        this.cardPan = cardPan;
        return this;
    }

    public void setCardPan(String cardPan) {
        this.cardPan = cardPan;
    }

    public String getCardValidityDate() {
        return cardValidityDate;
    }

    public Meansofpayment cardValidityDate(String cardValidityDate) {
        this.cardValidityDate = cardValidityDate;
        return this;
    }

    public void setCardValidityDate(String cardValidityDate) {
        this.cardValidityDate = cardValidityDate;
    }

    public String getMomoAccount() {
        return momoAccount;
    }

    public Meansofpayment momoAccount(String momoAccount) {
        this.momoAccount = momoAccount;
        return this;
    }

    public void setMomoAccount(String momoAccount) {
        this.momoAccount = momoAccount;
    }

    public String getMeansofpaymentTypeCode() {
        return meansofpaymentTypeCode;
    }

    public Meansofpayment meansofpaymentTypeCode(String meansofpaymentTypeCode) {
        this.meansofpaymentTypeCode = meansofpaymentTypeCode;
        return this;
    }

    public void setMeansofpaymentTypeCode(String meansofpaymentTypeCode) {
        this.meansofpaymentTypeCode = meansofpaymentTypeCode;
    }

    public String getIssuerCode() {
        return issuerCode;
    }

    public Meansofpayment issuerCode(String issuerCode) {
        this.issuerCode = issuerCode;
        return this;
    }

    public void setIssuerCode(String issuerCode) {
        this.issuerCode = issuerCode;
    }

    public Boolean isActive() {
        return active;
    }

    public Meansofpayment active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Meansofpayment customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meansofpayment)) {
            return false;
        }
        return id != null && id.equals(((Meansofpayment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Meansofpayment{" +
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
            "}";
    }
}
