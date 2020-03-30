package com.electron.mfs.pg.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.Pg8583Status} entity.
 */
public class Pg8583StatusDTO implements Serializable {

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

        Pg8583StatusDTO pg8583StatusDTO = (Pg8583StatusDTO) o;
        if (pg8583StatusDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pg8583StatusDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pg8583StatusDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", defaultReason='" + getDefaultReason() + "'" +
            "}";
    }
}
