package com.electron.mfs.pg.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.Pg8583Callback} entity.
 */
public class Pg8583CallbackDTO implements Serializable {

    private Long id;

    @Size(max = 5)
    private String partnerCode;

    @NotNull
    @Size(max = 255)
    private String callbackUri;

    @NotNull
    @Size(max = 10)
    private String httpMethod;

    @NotNull
    @Size(max = 255)
    private String managerClass;

    @NotNull
    private Boolean active;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getCallbackUri() {
        return callbackUri;
    }

    public void setCallbackUri(String callbackUri) {
        this.callbackUri = callbackUri;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getManagerClass() {
        return managerClass;
    }

    public void setManagerClass(String managerClass) {
        this.managerClass = managerClass;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Pg8583CallbackDTO pg8583CallbackDTO = (Pg8583CallbackDTO) o;
        if (pg8583CallbackDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pg8583CallbackDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Pg8583CallbackDTO{" +
            "id=" + getId() +
            ", partnerCode='" + getPartnerCode() + "'" +
            ", callbackUri='" + getCallbackUri() + "'" +
            ", httpMethod='" + getHttpMethod() + "'" +
            ", managerClass='" + getManagerClass() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
