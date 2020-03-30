package com.electron.mfs.pg.gateway.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PgChannel.
 */
@Entity
@Table(name = "t_pg_channel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PgChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 5)
    @Column(name = "code", length = 5, nullable = false)
    private String code;

    @NotNull
    @Size(max = 50)
    @Column(name = "long_label", length = 50, nullable = false)
    private String longLabel;

    @NotNull
    @Size(max = 25)
    @Column(name = "short_label", length = 25, nullable = false)
    private String shortLabel;

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

    public PgChannel code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLongLabel() {
        return longLabel;
    }

    public PgChannel longLabel(String longLabel) {
        this.longLabel = longLabel;
        return this;
    }

    public void setLongLabel(String longLabel) {
        this.longLabel = longLabel;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public PgChannel shortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
        return this;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public Boolean isActive() {
        return active;
    }

    public PgChannel active(Boolean active) {
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
        if (!(o instanceof PgChannel)) {
            return false;
        }
        return id != null && id.equals(((PgChannel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PgChannel{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", longLabel='" + getLongLabel() + "'" +
            ", shortLabel='" + getShortLabel() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
