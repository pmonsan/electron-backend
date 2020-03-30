package com.electron.mfs.pg.services.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.services.domain.ServiceChannel} entity.
 */
public class ServiceChannelDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String channelCode;

    @NotNull
    private Boolean active;


    private Long pgServiceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
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

        ServiceChannelDTO serviceChannelDTO = (ServiceChannelDTO) o;
        if (serviceChannelDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceChannelDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceChannelDTO{" +
            "id=" + getId() +
            ", channelCode='" + getChannelCode() + "'" +
            ", active='" + isActive() + "'" +
            ", pgService=" + getPgServiceId() +
            "}";
    }
}
