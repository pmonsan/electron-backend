package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Beneficiary.
 */
@Entity
@Table(name = "t_beneficiary")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Beneficiary implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "number", length = 10, nullable = false)
    private String number;

    @NotNull
    @Column(name = "is_company", nullable = false)
    private Boolean isCompany;

    @Size(max = 50)
    @Column(name = "firstname", length = 50)
    private String firstname;

    @Size(max = 50)
    @Column(name = "name", length = 50)
    private String name;

    @NotNull
    @Size(max = 50)
    @Column(name = "alias_account", length = 50, nullable = false)
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

    @NotNull
    @Column(name = "is_dm_account", nullable = false)
    private Boolean isDmAccount;

    @Size(max = 20)
    @Column(name = "momo_account_number", length = 20)
    private String momoAccountNumber;

    @NotNull
    @Size(max = 5)
    @Column(name = "beneficiary_relationship_code", length = 5, nullable = false)
    private String beneficiaryRelationshipCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "beneficiary_type_code", length = 5, nullable = false)
    private String beneficiaryTypeCode;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("beneficiaries")
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

    public Beneficiary number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean isIsCompany() {
        return isCompany;
    }

    public Beneficiary isCompany(Boolean isCompany) {
        this.isCompany = isCompany;
        return this;
    }

    public void setIsCompany(Boolean isCompany) {
        this.isCompany = isCompany;
    }

    public String getFirstname() {
        return firstname;
    }

    public Beneficiary firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public Beneficiary name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAliasAccount() {
        return aliasAccount;
    }

    public Beneficiary aliasAccount(String aliasAccount) {
        this.aliasAccount = aliasAccount;
        return this;
    }

    public void setAliasAccount(String aliasAccount) {
        this.aliasAccount = aliasAccount;
    }

    public String getBaccBankCode() {
        return baccBankCode;
    }

    public Beneficiary baccBankCode(String baccBankCode) {
        this.baccBankCode = baccBankCode;
        return this;
    }

    public void setBaccBankCode(String baccBankCode) {
        this.baccBankCode = baccBankCode;
    }

    public String getBaccBranchCode() {
        return baccBranchCode;
    }

    public Beneficiary baccBranchCode(String baccBranchCode) {
        this.baccBranchCode = baccBranchCode;
        return this;
    }

    public void setBaccBranchCode(String baccBranchCode) {
        this.baccBranchCode = baccBranchCode;
    }

    public String getBaccAccountNumber() {
        return baccAccountNumber;
    }

    public Beneficiary baccAccountNumber(String baccAccountNumber) {
        this.baccAccountNumber = baccAccountNumber;
        return this;
    }

    public void setBaccAccountNumber(String baccAccountNumber) {
        this.baccAccountNumber = baccAccountNumber;
    }

    public String getBaccRibKey() {
        return baccRibKey;
    }

    public Beneficiary baccRibKey(String baccRibKey) {
        this.baccRibKey = baccRibKey;
        return this;
    }

    public void setBaccRibKey(String baccRibKey) {
        this.baccRibKey = baccRibKey;
    }

    public String getCardCvv2() {
        return cardCvv2;
    }

    public Beneficiary cardCvv2(String cardCvv2) {
        this.cardCvv2 = cardCvv2;
        return this;
    }

    public void setCardCvv2(String cardCvv2) {
        this.cardCvv2 = cardCvv2;
    }

    public String getCardPan() {
        return cardPan;
    }

    public Beneficiary cardPan(String cardPan) {
        this.cardPan = cardPan;
        return this;
    }

    public void setCardPan(String cardPan) {
        this.cardPan = cardPan;
    }

    public String getCardValidityDate() {
        return cardValidityDate;
    }

    public Beneficiary cardValidityDate(String cardValidityDate) {
        this.cardValidityDate = cardValidityDate;
        return this;
    }

    public void setCardValidityDate(String cardValidityDate) {
        this.cardValidityDate = cardValidityDate;
    }

    public Boolean isIsDmAccount() {
        return isDmAccount;
    }

    public Beneficiary isDmAccount(Boolean isDmAccount) {
        this.isDmAccount = isDmAccount;
        return this;
    }

    public void setIsDmAccount(Boolean isDmAccount) {
        this.isDmAccount = isDmAccount;
    }

    public String getMomoAccountNumber() {
        return momoAccountNumber;
    }

    public Beneficiary momoAccountNumber(String momoAccountNumber) {
        this.momoAccountNumber = momoAccountNumber;
        return this;
    }

    public void setMomoAccountNumber(String momoAccountNumber) {
        this.momoAccountNumber = momoAccountNumber;
    }

    public String getBeneficiaryRelationshipCode() {
        return beneficiaryRelationshipCode;
    }

    public Beneficiary beneficiaryRelationshipCode(String beneficiaryRelationshipCode) {
        this.beneficiaryRelationshipCode = beneficiaryRelationshipCode;
        return this;
    }

    public void setBeneficiaryRelationshipCode(String beneficiaryRelationshipCode) {
        this.beneficiaryRelationshipCode = beneficiaryRelationshipCode;
    }

    public String getBeneficiaryTypeCode() {
        return beneficiaryTypeCode;
    }

    public Beneficiary beneficiaryTypeCode(String beneficiaryTypeCode) {
        this.beneficiaryTypeCode = beneficiaryTypeCode;
        return this;
    }

    public void setBeneficiaryTypeCode(String beneficiaryTypeCode) {
        this.beneficiaryTypeCode = beneficiaryTypeCode;
    }

    public Boolean isActive() {
        return active;
    }

    public Beneficiary active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Beneficiary customer(Customer customer) {
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
        if (!(o instanceof Beneficiary)) {
            return false;
        }
        return id != null && id.equals(((Beneficiary) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Beneficiary{" +
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
            "}";
    }
}
