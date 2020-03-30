package com.electron.mfs.pg.account.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.account.domain.ContractOpposition} entity.
 */
public class ContractOppositionDTO implements Serializable {

    private Long id;

    @Size(max = 25)
    private String number;

    @NotNull
    private Boolean isCustomerInitiative;

    @NotNull
    private Instant oppositionDate;

    @Size(max = 255)
    private String oppositionReason;

    @Size(max = 255)
    private String comment;

    @NotNull
    private Boolean active;


    private Long contractId;

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

    public Boolean isIsCustomerInitiative() {
        return isCustomerInitiative;
    }

    public void setIsCustomerInitiative(Boolean isCustomerInitiative) {
        this.isCustomerInitiative = isCustomerInitiative;
    }

    public Instant getOppositionDate() {
        return oppositionDate;
    }

    public void setOppositionDate(Instant oppositionDate) {
        this.oppositionDate = oppositionDate;
    }

    public String getOppositionReason() {
        return oppositionReason;
    }

    public void setOppositionReason(String oppositionReason) {
        this.oppositionReason = oppositionReason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContractOppositionDTO contractOppositionDTO = (ContractOppositionDTO) o;
        if (contractOppositionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contractOppositionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContractOppositionDTO{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", isCustomerInitiative='" + isIsCustomerInitiative() + "'" +
            ", oppositionDate='" + getOppositionDate() + "'" +
            ", oppositionReason='" + getOppositionReason() + "'" +
            ", comment='" + getComment() + "'" +
            ", active='" + isActive() + "'" +
            ", contract=" + getContractId() +
            "}";
    }
}
