package com.electron.mfs.pg.limits.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.electron.mfs.pg.limits.domain.enumeration.LimitValueType;

/**
 * A DTO for the {@link com.electron.mfs.pg.limits.domain.LimitType} entity.
 */
public class LimitTypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String code;

    @NotNull
    @Size(max = 100)
    private String label;

    @NotNull
    private LimitValueType limitValueType;

    @NotNull
    private Boolean active;


    private Long periodicityId;

    private Long limitMeasureId;

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

    public LimitValueType getLimitValueType() {
        return limitValueType;
    }

    public void setLimitValueType(LimitValueType limitValueType) {
        this.limitValueType = limitValueType;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getPeriodicityId() {
        return periodicityId;
    }

    public void setPeriodicityId(Long periodicityId) {
        this.periodicityId = periodicityId;
    }

    public Long getLimitMeasureId() {
        return limitMeasureId;
    }

    public void setLimitMeasureId(Long limitMeasureId) {
        this.limitMeasureId = limitMeasureId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LimitTypeDTO limitTypeDTO = (LimitTypeDTO) o;
        if (limitTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), limitTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LimitTypeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", limitValueType='" + getLimitValueType() + "'" +
            ", active='" + isActive() + "'" +
            ", periodicity=" + getPeriodicityId() +
            ", limitMeasure=" + getLimitMeasureId() +
            "}";
    }
}
