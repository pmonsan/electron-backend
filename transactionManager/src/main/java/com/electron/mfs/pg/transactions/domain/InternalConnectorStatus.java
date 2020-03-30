package com.electron.mfs.pg.transactions.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A InternalConnectorStatus.
 */
@Entity
@Table(name = "t_internal_connector_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InternalConnectorStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "status", length = 100, nullable = false)
    private String status;

    @Size(max = 100)
    @Column(name = "default_reason", length = 100)
    private String defaultReason;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public InternalConnectorStatus status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDefaultReason() {
        return defaultReason;
    }

    public InternalConnectorStatus defaultReason(String defaultReason) {
        this.defaultReason = defaultReason;
        return this;
    }

    public void setDefaultReason(String defaultReason) {
        this.defaultReason = defaultReason;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InternalConnectorStatus)) {
            return false;
        }
        return id != null && id.equals(((InternalConnectorStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "InternalConnectorStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", defaultReason='" + getDefaultReason() + "'" +
            "}";
    }
}
