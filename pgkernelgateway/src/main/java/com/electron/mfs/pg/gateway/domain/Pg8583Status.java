package com.electron.mfs.pg.gateway.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Pg8583Status.
 */
@Entity
@Table(name = "t_pg_8583_status")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pg8583Status implements Serializable {

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

    public Pg8583Status status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDefaultReason() {
        return defaultReason;
    }

    public Pg8583Status defaultReason(String defaultReason) {
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
        if (!(o instanceof Pg8583Status)) {
            return false;
        }
        return id != null && id.equals(((Pg8583Status) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Pg8583Status{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", defaultReason='" + getDefaultReason() + "'" +
            "}";
    }
}
