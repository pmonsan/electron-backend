package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PgDetailMessage.
 */
@Entity
@Table(name = "t_pg_message_detail")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PgDetailMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "data_value", nullable = false)
    private String dataValue;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("pgDetailMessages")
    private PgData pgData;

    @ManyToOne
    @JsonIgnoreProperties("pgDetailMessages")
    private PgMessage pgMessage;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataValue() {
        return dataValue;
    }

    public PgDetailMessage dataValue(String dataValue) {
        this.dataValue = dataValue;
        return this;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public Boolean isActive() {
        return active;
    }

    public PgDetailMessage active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public PgData getPgData() {
        return pgData;
    }

    public PgDetailMessage pgData(PgData pgData) {
        this.pgData = pgData;
        return this;
    }

    public void setPgData(PgData pgData) {
        this.pgData = pgData;
    }

    public PgMessage getPgMessage() {
        return pgMessage;
    }

    public PgDetailMessage pgMessage(PgMessage pgMessage) {
        this.pgMessage = pgMessage;
        return this;
    }

    public void setPgMessage(PgMessage pgMessage) {
        this.pgMessage = pgMessage;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PgDetailMessage)) {
            return false;
        }
        return id != null && id.equals(((PgDetailMessage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PgDetailMessage{" +
            "id=" + getId() +
            ", dataValue='" + getDataValue() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
