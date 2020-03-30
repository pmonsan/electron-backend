package com.electron.mfs.pg.services.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Connector.
 */
@Entity
@Table(name = "t_connector")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Connector implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @NotNull
    @Size(max = 50)
    @Column(name = "label", length = 50, nullable = false)
    private String label;

    @Column(name = "logic")
    private String logic;

    @Column(name = "comment")
    private String comment;

    @Size(max = 5)
    @Column(name = "partner_code", length = 5)
    private String partnerCode;

    @NotNull
    @Size(max = 5)
    @Column(name = "meansofpayment_type_code", length = 5, nullable = false)
    private String meansofpaymentTypeCode;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("connectors")
    private ConnectorType connectorType;

    @ManyToOne
    @JsonIgnoreProperties("connectors")
    private PgModule pgModule;

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

    public Connector code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public Connector label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLogic() {
        return logic;
    }

    public Connector logic(String logic) {
        this.logic = logic;
        return this;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public String getComment() {
        return comment;
    }

    public Connector comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public Connector partnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
        return this;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getMeansofpaymentTypeCode() {
        return meansofpaymentTypeCode;
    }

    public Connector meansofpaymentTypeCode(String meansofpaymentTypeCode) {
        this.meansofpaymentTypeCode = meansofpaymentTypeCode;
        return this;
    }

    public void setMeansofpaymentTypeCode(String meansofpaymentTypeCode) {
        this.meansofpaymentTypeCode = meansofpaymentTypeCode;
    }

    public Boolean isActive() {
        return active;
    }

    public Connector active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ConnectorType getConnectorType() {
        return connectorType;
    }

    public Connector connectorType(ConnectorType connectorType) {
        this.connectorType = connectorType;
        return this;
    }

    public void setConnectorType(ConnectorType connectorType) {
        this.connectorType = connectorType;
    }

    public PgModule getPgModule() {
        return pgModule;
    }

    public Connector pgModule(PgModule pgModule) {
        this.pgModule = pgModule;
        return this;
    }

    public void setPgModule(PgModule pgModule) {
        this.pgModule = pgModule;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Connector)) {
            return false;
        }
        return id != null && id.equals(((Connector) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Connector{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", logic='" + getLogic() + "'" +
            ", comment='" + getComment() + "'" +
            ", partnerCode='" + getPartnerCode() + "'" +
            ", meansofpaymentTypeCode='" + getMeansofpaymentTypeCode() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
