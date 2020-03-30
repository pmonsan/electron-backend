package com.electron.mfs.pg.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.PgDetailMessage} entity.
 */
public class PgDetailMessageDTO implements Serializable {

    private Long id;

    @NotNull
    private String dataValue;

    @NotNull
    private Boolean active;


    private Long pgDataId;

    private Long pgMessageId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
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

    public Long getPgMessageId() {
        return pgMessageId;
    }

    public void setPgMessageId(Long pgMessageId) {
        this.pgMessageId = pgMessageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PgDetailMessageDTO pgDetailMessageDTO = (PgDetailMessageDTO) o;
        if (pgDetailMessageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pgDetailMessageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PgDetailMessageDTO{" +
            "id=" + getId() +
            ", dataValue='" + getDataValue() + "'" +
            ", active='" + isActive() + "'" +
            ", pgData=" + getPgDataId() +
            ", pgMessage=" + getPgMessageId() +
            "}";
    }
}
