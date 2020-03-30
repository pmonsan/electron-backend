package com.electron.mfs.pg.mdm.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PgBatch.
 */
@Entity
@Table(name = "t_batch")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PgBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 5)
    @Column(name = "code", length = 5, nullable = false)
    private String code;

    @Size(max = 100)
    @Column(name = "label", length = 100)
    private String label;

    @NotNull
    @Column(name = "expected_end_date", nullable = false)
    private Instant expectedEndDate;

    @NotNull
    @Column(name = "batch_date", nullable = false)
    private Instant batchDate;

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

    public String getCode() {
        return code;
    }

    public PgBatch code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public PgBatch label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Instant getExpectedEndDate() {
        return expectedEndDate;
    }

    public PgBatch expectedEndDate(Instant expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
        return this;
    }

    public void setExpectedEndDate(Instant expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public Instant getBatchDate() {
        return batchDate;
    }

    public PgBatch batchDate(Instant batchDate) {
        this.batchDate = batchDate;
        return this;
    }

    public void setBatchDate(Instant batchDate) {
        this.batchDate = batchDate;
    }

    public Boolean isActive() {
        return active;
    }

    public PgBatch active(Boolean active) {
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
        if (!(o instanceof PgBatch)) {
            return false;
        }
        return id != null && id.equals(((PgBatch) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PgBatch{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", expectedEndDate='" + getExpectedEndDate() + "'" +
            ", batchDate='" + getBatchDate() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
