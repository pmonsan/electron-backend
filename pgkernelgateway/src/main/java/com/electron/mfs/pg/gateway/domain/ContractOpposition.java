package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A ContractOpposition.
 */
@Entity
@Table(name = "t_contract_opposition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContractOpposition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 25)
    @Column(name = "number", length = 25)
    private String number;

    @NotNull
    @Column(name = "is_customer_initiative", nullable = false)
    private Boolean isCustomerInitiative;

    @NotNull
    @Column(name = "opposition_date", nullable = false)
    private Instant oppositionDate;

    @Size(max = 255)
    @Column(name = "opposition_reason", length = 255)
    private String oppositionReason;

    @Size(max = 255)
    @Column(name = "comment", length = 255)
    private String comment;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("contractOppositions")
    private Contract contract;

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

    public ContractOpposition number(String number) {
        this.number = number;
        return this;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean isIsCustomerInitiative() {
        return isCustomerInitiative;
    }

    public ContractOpposition isCustomerInitiative(Boolean isCustomerInitiative) {
        this.isCustomerInitiative = isCustomerInitiative;
        return this;
    }

    public void setIsCustomerInitiative(Boolean isCustomerInitiative) {
        this.isCustomerInitiative = isCustomerInitiative;
    }

    public Instant getOppositionDate() {
        return oppositionDate;
    }

    public ContractOpposition oppositionDate(Instant oppositionDate) {
        this.oppositionDate = oppositionDate;
        return this;
    }

    public void setOppositionDate(Instant oppositionDate) {
        this.oppositionDate = oppositionDate;
    }

    public String getOppositionReason() {
        return oppositionReason;
    }

    public ContractOpposition oppositionReason(String oppositionReason) {
        this.oppositionReason = oppositionReason;
        return this;
    }

    public void setOppositionReason(String oppositionReason) {
        this.oppositionReason = oppositionReason;
    }

    public String getComment() {
        return comment;
    }

    public ContractOpposition comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isActive() {
        return active;
    }

    public ContractOpposition active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Contract getContract() {
        return contract;
    }

    public ContractOpposition contract(Contract contract) {
        this.contract = contract;
        return this;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContractOpposition)) {
            return false;
        }
        return id != null && id.equals(((ContractOpposition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ContractOpposition{" +
            "id=" + getId() +
            ", number='" + getNumber() + "'" +
            ", isCustomerInitiative='" + isIsCustomerInitiative() + "'" +
            ", oppositionDate='" + getOppositionDate() + "'" +
            ", oppositionReason='" + getOppositionReason() + "'" +
            ", comment='" + getComment() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
