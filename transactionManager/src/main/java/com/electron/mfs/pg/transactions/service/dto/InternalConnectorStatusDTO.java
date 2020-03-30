package com.electron.mfs.pg.transactions.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.transactions.domain.InternalConnectorStatus} entity.
 */
public class InternalConnectorStatusDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String status;

    @Size(max = 100)
    private String defaultReason;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDefaultReason() {
        return defaultReason;
    }

    public void setDefaultReason(String defaultReason) {
        this.defaultReason = defaultReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InternalConnectorStatusDTO internalConnectorStatusDTO = (InternalConnectorStatusDTO) o;
        if (internalConnectorStatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), internalConnectorStatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InternalConnectorStatusDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", defaultReason='" + getDefaultReason() + "'" +
            "}";
    }
}
