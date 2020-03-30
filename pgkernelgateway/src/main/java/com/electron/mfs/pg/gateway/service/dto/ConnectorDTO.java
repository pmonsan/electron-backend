package com.electron.mfs.pg.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.Connector} entity.
 */
public class ConnectorDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String code;

    @NotNull
    @Size(max = 50)
    private String label;

    private String logic;

    private String comment;

    @Size(max = 5)
    private String partnerCode;

    @NotNull
    @Size(max = 5)
    private String meansofpaymentTypeCode;

    @NotNull
    private Boolean active;


    private Long connectorTypeId;

    private Long pgModuleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getMeansofpaymentTypeCode() {
        return meansofpaymentTypeCode;
    }

    public void setMeansofpaymentTypeCode(String meansofpaymentTypeCode) {
        this.meansofpaymentTypeCode = meansofpaymentTypeCode;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getConnectorTypeId() {
        return connectorTypeId;
    }

    public void setConnectorTypeId(Long connectorTypeId) {
        this.connectorTypeId = connectorTypeId;
    }

    public Long getPgModuleId() {
        return pgModuleId;
    }

    public void setPgModuleId(Long pgModuleId) {
        this.pgModuleId = pgModuleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConnectorDTO connectorDTO = (ConnectorDTO) o;
        if (connectorDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), connectorDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConnectorDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", logic='" + getLogic() + "'" +
            ", comment='" + getComment() + "'" +
            ", partnerCode='" + getPartnerCode() + "'" +
            ", meansofpaymentTypeCode='" + getMeansofpaymentTypeCode() + "'" +
            ", active='" + isActive() + "'" +
            ", connectorType=" + getConnectorTypeId() +
            ", pgModule=" + getPgModuleId() +
            "}";
    }
}
