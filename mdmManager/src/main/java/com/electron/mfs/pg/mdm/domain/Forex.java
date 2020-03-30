package com.electron.mfs.pg.mdm.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Forex.
 */
@Entity
@Table(name = "t_forex")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Forex implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 5)
    @Column(name = "code", length = 5, nullable = false)
    private String code;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "creation_date")
    private Instant creationDate;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("forexes")
    private Currency fromCurrency;

    @ManyToOne
    @JsonIgnoreProperties("forexes")
    private Currency toCurrency;

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

    public Forex code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getRate() {
        return rate;
    }

    public Forex rate(Double rate) {
        this.rate = rate;
        return this;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public Forex creationDate(Instant creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isActive() {
        return active;
    }

    public Forex active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Currency getFromCurrency() {
        return fromCurrency;
    }

    public Forex fromCurrency(Currency currency) {
        this.fromCurrency = currency;
        return this;
    }

    public void setFromCurrency(Currency currency) {
        this.fromCurrency = currency;
    }

    public Currency getToCurrency() {
        return toCurrency;
    }

    public Forex toCurrency(Currency currency) {
        this.toCurrency = currency;
        return this;
    }

    public void setToCurrency(Currency currency) {
        this.toCurrency = currency;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Forex)) {
            return false;
        }
        return id != null && id.equals(((Forex) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Forex{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", rate=" + getRate() +
            ", creationDate='" + getCreationDate() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
