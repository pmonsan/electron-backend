package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.electron.mfs.pg.gateway.domain.enumeration.CustomerBlacklistStatus;

/**
 * A CustomerBlacklist.
 */
@Entity
@Table(name = "t_customer_blacklist")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerBlacklist implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "customer_blacklist_status", nullable = false)
    private CustomerBlacklistStatus customerBlacklistStatus;

    @NotNull
    @Column(name = "insertion_date", nullable = false)
    private Instant insertionDate;

    @Column(name = "modification_date")
    private Instant modificationDate;

    @Size(max = 255)
    @Column(name = "comment", length = 255)
    private String comment;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("customerBlacklists")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerBlacklistStatus getCustomerBlacklistStatus() {
        return customerBlacklistStatus;
    }

    public CustomerBlacklist customerBlacklistStatus(CustomerBlacklistStatus customerBlacklistStatus) {
        this.customerBlacklistStatus = customerBlacklistStatus;
        return this;
    }

    public void setCustomerBlacklistStatus(CustomerBlacklistStatus customerBlacklistStatus) {
        this.customerBlacklistStatus = customerBlacklistStatus;
    }

    public Instant getInsertionDate() {
        return insertionDate;
    }

    public CustomerBlacklist insertionDate(Instant insertionDate) {
        this.insertionDate = insertionDate;
        return this;
    }

    public void setInsertionDate(Instant insertionDate) {
        this.insertionDate = insertionDate;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public CustomerBlacklist modificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
    }

    public String getComment() {
        return comment;
    }

    public CustomerBlacklist comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isActive() {
        return active;
    }

    public CustomerBlacklist active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerBlacklist customer(Customer customer) {
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
        if (!(o instanceof CustomerBlacklist)) {
            return false;
        }
        return id != null && id.equals(((CustomerBlacklist) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CustomerBlacklist{" +
            "id=" + getId() +
            ", customerBlacklistStatus='" + getCustomerBlacklistStatus() + "'" +
            ", insertionDate='" + getInsertionDate() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
