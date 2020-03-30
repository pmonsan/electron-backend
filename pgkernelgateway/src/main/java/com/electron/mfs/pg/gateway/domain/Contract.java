package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Contract.
 */
@Entity
@Table(name = "t_contract")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 25)
    @Column(name = "number", length = 25)
    private String number;

    @NotNull
    @Column(name = "creation_date", nullable = false)
    private Instant creationDate;

    @NotNull
    @Column(name = "is_merchant_contract", nullable = false)
    private Boolean isMerchantContract;

    @NotNull
    @Column(name = "modification_date", nullable = false)
    private Instant modificationDate;

    @Column(name = "validation_date")
    private Instant validationDate;

    @NotNull
    @Size(max = 100)
    @Column(name = "filename", length = 100, nullable = false)
    private String filename;

    @NotNull
    @Size(max = 5)
    @Column(name = "customer_code", length = 5, nullable = false)
    private String customerCode;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("contracts")
    private PgAccount account;

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

    public Contract number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Contract creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isIsMerchantContract() {
        return isMerchantContract;
    }

    public Contract isMerchantContract(Boolean isMerchantContract) {
        this.isMerchantContract = isMerchantContract;
        return this;
    }

    public void setIsMerchantContract(Boolean isMerchantContract) {
        this.isMerchantContract = isMerchantContract;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public Contract modificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Instant getValidationDate() {
        return validationDate;
    }

    public Contract validationDate(Instant validationDate) {
        this.validationDate = validationDate;
        return this;
    }

    public void setValidationDate(Instant validationDate) {
        this.validationDate = validationDate;
    }

    public String getFilename() {
        return filename;
    }

    public Contract filename(String filename) {
        this.filename = filename;
        return this;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public Contract customerCode(String customerCode) {
        this.customerCode = customerCode;
        return this;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Boolean isActive() {
        return active;
    }

    public Contract active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public PgAccount getAccount() {
        return account;
    }

    public Contract account(PgAccount pgAccount) {
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
        if (!(o instanceof Contract)) {
            return false;
        }
        return id != null && id.equals(((Contract) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contract{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", isMerchantContract='" + isIsMerchantContract() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            ", validationDate='" + getValidationDate() + "'" +
            ", filename='" + getFilename() + "'" +
            ", customerCode='" + getCustomerCode() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
