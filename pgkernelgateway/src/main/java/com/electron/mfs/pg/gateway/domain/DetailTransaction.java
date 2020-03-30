package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A DetailTransaction.
 */
@Entity
@Table(name = "t_transaction_detail")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class DetailTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 5)
    @Column(name = "pg_data_code", length = 5, nullable = false)
    private String pgDataCode;

    @Column(name = "data_value")
    private String dataValue;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("detailTransactions")
    private Transaction transaction;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPgDataCode() {
        return pgDataCode;
    }

    public DetailTransaction pgDataCode(String pgDataCode) {
        this.pgDataCode = pgDataCode;
        return this;
    }

    public void setPgDataCode(String pgDataCode) {
        this.pgDataCode = pgDataCode;
    }

    public String getDataValue() {
        return dataValue;
    }

    public DetailTransaction dataValue(String dataValue) {
        this.dataValue = dataValue;
        return this;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public Boolean isActive() {
        return active;
    }

    public DetailTransaction active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public DetailTransaction transaction(Transaction transaction) {
        this.transaction = transaction;
        return this;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DetailTransaction)) {
            return false;
        }
        return id != null && id.equals(((DetailTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DetailTransaction{" +
            "id=" + getId() +
            ", pgDataCode='" + getPgDataCode() + "'" +
            ", dataValue='" + getDataValue() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
