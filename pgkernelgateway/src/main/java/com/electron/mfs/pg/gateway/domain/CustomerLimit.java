package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A CustomerLimit.
 */
@Entity
@Table(name = "t_plafond_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CustomerLimit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 5)
    @Column(name = "limit_type_code", length = 5, nullable = false)
    private String limitTypeCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "account_type_code", length = 5, nullable = false)
    private String accountTypeCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "customer_type_code", length = 5, nullable = false)
    private String customerTypeCode;

    @Column(name = "value")
    private Double value;

    @Size(max = 255)
    @Column(name = "comment", length = 255)
    private String comment;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("customerLimits")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLimitTypeCode() {
        return limitTypeCode;
    }

    public CustomerLimit limitTypeCode(String limitTypeCode) {
        this.limitTypeCode = limitTypeCode;
        return this;
    }

    public void setLimitTypeCode(String limitTypeCode) {
        this.limitTypeCode = limitTypeCode;
    }

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public CustomerLimit accountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
        return this;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public String getCustomerTypeCode() {
        return customerTypeCode;
    }

    public CustomerLimit customerTypeCode(String customerTypeCode) {
        this.customerTypeCode = customerTypeCode;
        return this;
    }

    public void setCustomerTypeCode(String customerTypeCode) {
        this.customerTypeCode = customerTypeCode;
    }

    public Double getValue() {
        return value;
    }

    public CustomerLimit value(Double value) {
        this.value = value;
        return this;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public CustomerLimit comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isActive() {
        return active;
    }

    public CustomerLimit active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Customer getCustomer() {
        return customer;
    }

    public CustomerLimit customer(Customer customer) {
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
        if (!(o instanceof CustomerLimit)) {
            return false;
        }
        return id != null && id.equals(((CustomerLimit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CustomerLimit{" +
            "id=" + getId() +
            ", limitTypeCode='" + getLimitTypeCode() + "'" +
            ", accountTypeCode='" + getAccountTypeCode() + "'" +
            ", customerTypeCode='" + getCustomerTypeCode() + "'" +
            ", value=" + getValue() +
            ", comment='" + getComment() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
