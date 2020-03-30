package com.electron.mfs.pg.services.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.services.domain.ServiceLimit} entity.
 */
public class ServiceLimitDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String limitTypeCode;

    @NotNull
    private String value;

    @Size(max = 255)
    private String comment;

    @NotNull
    private Boolean active;


    private Long pgServiceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLimitTypeCode() {
        return limitTypeCode;
    }

    public void setLimitTypeCode(String limitTypeCode) {
        this.limitTypeCode = limitTypeCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getPgServiceId() {
        return pgServiceId;
    }

    public void setPgServiceId(Long pgServiceId) {
        this.pgServiceId = pgServiceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceLimitDTO serviceLimitDTO = (ServiceLimitDTO) o;
        if (serviceLimitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceLimitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceLimitDTO{" +
            "id=" + getId() +
            ", limitTypeCode='" + getLimitTypeCode() + "'" +
            ", value='" + getValue() + "'" +
            ", comment='" + getComment() + "'" +
            ", active='" + isActive() + "'" +
            ", pgService=" + getPgServiceId() +
            "}";
    }
}
