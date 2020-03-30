package com.electron.mfs.pg.gateway.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Pg8583Callback.
 */
@Entity
@Table(name = "t_pg_8583_callback")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pg8583Callback implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 5)
    @Column(name = "partner_code", length = 5)
    private String partnerCode;

    @NotNull
    @Size(max = 255)
    @Column(name = "callback_uri", length = 255, nullable = false)
    private String callbackUri;

    @NotNull
    @Size(max = 10)
    @Column(name = "http_method", length = 10, nullable = false)
    private String httpMethod;

    @NotNull
    @Size(max = 255)
    @Column(name = "manager_class", length = 255, nullable = false)
    private String managerClass;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public Pg8583Callback partnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
        return this;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getCallbackUri() {
        return callbackUri;
    }

    public Pg8583Callback callbackUri(String callbackUri) {
        this.callbackUri = callbackUri;
        return this;
    }

    public void setCallbackUri(String callbackUri) {
        this.callbackUri = callbackUri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public Pg8583Callback httpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getManagerClass() {
        return managerClass;
    }

    public Pg8583Callback managerClass(String managerClass) {
        this.managerClass = managerClass;
        return this;
    }

    public void setManagerClass(String managerClass) {
        this.managerClass = managerClass;
    }

    public Boolean isActive() {
        return active;
    }

    public Pg8583Callback active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pg8583Callback)) {
            return false;
        }
        return id != null && id.equals(((Pg8583Callback) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pg8583Callback{" +
            "id=" + getId() +
            ", partnerCode='" + getPartnerCode() + "'" +
            ", callbackUri='" + getCallbackUri() + "'" +
            ", httpMethod='" + getHttpMethod() + "'" +
            ", managerClass='" + getManagerClass() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
