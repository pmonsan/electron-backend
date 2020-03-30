package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ServiceIntegration.
 */
@Entity
@Table(name = "t_service_integration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServiceIntegration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "customer_ref", length = 30, nullable = false)
    private String customerRef;

    @Size(max = 5)
    @Column(name = "service_code", length = 5)
    private String serviceCode;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("serviceIntegrations")
    private Partner partner;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerRef() {
        return customerRef;
    }

    public ServiceIntegration customerRef(String customerRef) {
        this.customerRef = customerRef;
        return this;
    }

    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public ServiceIntegration serviceCode(String serviceCode) {
        this.serviceCode = serviceCode;
        return this;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Boolean isActive() {
        return active;
    }

    public ServiceIntegration active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Partner getPartner() {
        return partner;
    }

    public ServiceIntegration partner(Partner partner) {
        this.partner = partner;
        return this;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceIntegration)) {
            return false;
        }
        return id != null && id.equals(((ServiceIntegration) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceIntegration{" +
            "id=" + getId() +
            ", customerRef='" + getCustomerRef() + "'" +
            ", serviceCode='" + getServiceCode() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
