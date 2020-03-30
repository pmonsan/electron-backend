package com.electron.mfs.pg.account.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.account.domain.Contract} entity.
 */
public class ContractDTO implements Serializable {

    private Long id;

    @Size(max = 25)
    private String number;

    @NotNull
    private Instant creationDate;

    @NotNull
    private Boolean isMerchantContract;

    @NotNull
    private Instant modificationDate;

    private Instant validationDate;

    @NotNull
    @Size(max = 100)
    private String filename;

    @NotNull
    @Size(max = 5)
    private String customerCode;

    @NotNull
    private Boolean active;


    private Long accountId;

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

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isIsMerchantContract() {
        return isMerchantContract;
    }

    public void setIsMerchantContract(Boolean isMerchantContract) {
        this.isMerchantContract = isMerchantContract;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Instant getValidationDate() {
        return validationDate;
    }

    public void setValidationDate(Instant validationDate) {
        this.validationDate = validationDate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long pgAccountId) {
        this.accountId = pgAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContractDTO contractDTO = (ContractDTO) o;
        if (contractDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contractDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContractDTO{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", creationDate='" + getCreationDate() + "'" +
            ", isMerchantContract='" + isIsMerchantContract() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            ", validationDate='" + getValidationDate() + "'" +
            ", filename='" + getFilename() + "'" +
            ", customerCode='" + getCustomerCode() + "'" +
            ", active='" + isActive() + "'" +
            ", account=" + getAccountId() +
            "}";
    }
}
