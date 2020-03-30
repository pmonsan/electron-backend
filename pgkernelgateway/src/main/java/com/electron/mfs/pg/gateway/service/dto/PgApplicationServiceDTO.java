package com.electron.mfs.pg.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.PgApplicationService} entity.
 */
public class PgApplicationServiceDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String serviceCode;

    @NotNull
    private Boolean active;


    private Long pgApplicationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getPgApplicationId() {
        return pgApplicationId;
    }

    public void setPgApplicationId(Long pgApplicationId) {
        this.pgApplicationId = pgApplicationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PgApplicationServiceDTO pgApplicationServiceDTO = (PgApplicationServiceDTO) o;
        if (pgApplicationServiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pgApplicationServiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PgApplicationServiceDTO{" +
            "id=" + getId() +
            ", serviceCode='" + getServiceCode() + "'" +
            ", active='" + isActive() + "'" +
            ", pgApplication=" + getPgApplicationId() +
            "}";
    }
}
