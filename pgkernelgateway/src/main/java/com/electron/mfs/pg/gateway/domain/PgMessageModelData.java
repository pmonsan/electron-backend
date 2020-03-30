package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PgMessageModelData.
 */
@Entity
@Table(name = "t_pg_message_model_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PgMessageModelData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "mandatory", nullable = false)
    private Boolean mandatory;

    @NotNull
    @Column(name = "hidden", nullable = false)
    private Boolean hidden;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("pgMessageModelData")
    private PgData pgData;

    @ManyToOne
    @JsonIgnoreProperties("pgMessageModelData")
    private PgMessageModel pgMessageModel;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isMandatory() {
        return mandatory;
    }

    public PgMessageModelData mandatory(Boolean mandatory) {
        this.mandatory = mandatory;
        return this;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public PgMessageModelData hidden(Boolean hidden) {
        this.hidden = hidden;
        return this;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean isActive() {
        return active;
    }

    public PgMessageModelData active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public PgData getPgData() {
        return pgData;
    }

    public PgMessageModelData pgData(PgData pgData) {
        this.pgData = pgData;
        return this;
    }

    public void setPgData(PgData pgData) {
        this.pgData = pgData;
    }

    public PgMessageModel getPgMessageModel() {
        return pgMessageModel;
    }

    public PgMessageModelData pgMessageModel(PgMessageModel pgMessageModel) {
        this.pgMessageModel = pgMessageModel;
        return this;
    }

    public void setPgMessageModel(PgMessageModel pgMessageModel) {
        this.pgMessageModel = pgMessageModel;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PgMessageModelData)) {
            return false;
        }
        return id != null && id.equals(((PgMessageModelData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PgMessageModelData{" +
            "id=" + getId() +
            ", mandatory='" + isMandatory() + "'" +
            ", hidden='" + isHidden() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
