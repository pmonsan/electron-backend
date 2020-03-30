package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.electron.mfs.pg.gateway.domain.enumeration.LimitValueType;

/**
 * A LimitType.
 */
@Entity
@Table(name = "t_limit_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LimitType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 5)
    @Column(name = "code", length = 5, nullable = false)
    private String code;

    @NotNull
    @Size(max = 100)
    @Column(name = "label", length = 100, nullable = false)
    private String label;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "limit_value_type", nullable = false)
    private LimitValueType limitValueType;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("limitTypes")
    private Periodicity periodicity;

    @ManyToOne
    @JsonIgnoreProperties("limitTypes")
    private LimitMeasure limitMeasure;

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

    public LimitType code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public LimitType label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public LimitValueType getLimitValueType() {
        return limitValueType;
    }

    public LimitType limitValueType(LimitValueType limitValueType) {
        this.limitValueType = limitValueType;
        return this;
    }

    public void setLimitValueType(LimitValueType limitValueType) {
        this.limitValueType = limitValueType;
    }

    public Boolean isActive() {
        return active;
    }

    public LimitType active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Periodicity getPeriodicity() {
        return periodicity;
    }

    public LimitType periodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
        return this;
    }

    public void setPeriodicity(Periodicity periodicity) {
        this.periodicity = periodicity;
    }

    public LimitMeasure getLimitMeasure() {
        return limitMeasure;
    }

    public LimitType limitMeasure(LimitMeasure limitMeasure) {
        this.limitMeasure = limitMeasure;
        return this;
    }

    public void setLimitMeasure(LimitMeasure limitMeasure) {
        this.limitMeasure = limitMeasure;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LimitType)) {
            return false;
        }
        return id != null && id.equals(((LimitType) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LimitType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", limitValueType='" + getLimitValueType() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
