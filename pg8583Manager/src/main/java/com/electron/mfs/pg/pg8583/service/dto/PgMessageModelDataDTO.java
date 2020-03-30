package com.electron.mfs.pg.pg8583.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.pg8583.domain.PgMessageModelData} entity.
 */
public class PgMessageModelDataDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean mandatory;

    @NotNull
    private Boolean hidden;

    @NotNull
    private Boolean active;


    private Long pgDataId;

    private Long pgMessageModelId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getPgDataId() {
        return pgDataId;
    }

    public void setPgDataId(Long pgDataId) {
        this.pgDataId = pgDataId;
    }

    public Long getPgMessageModelId() {
        return pgMessageModelId;
    }

    public void setPgMessageModelId(Long pgMessageModelId) {
        this.pgMessageModelId = pgMessageModelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PgMessageModelDataDTO pgMessageModelDataDTO = (PgMessageModelDataDTO) o;
        if (pgMessageModelDataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pgMessageModelDataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PgMessageModelDataDTO{" +
            "id=" + getId() +
            ", mandatory='" + isMandatory() + "'" +
            ", hidden='" + isHidden() + "'" +
            ", active='" + isActive() + "'" +
            ", pgData=" + getPgDataId() +
            ", pgMessageModel=" + getPgMessageModelId() +
            "}";
    }
}
