package com.electron.mfs.pg.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.ServiceIntegration} entity.
 */
public class ServiceIntegrationDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String customerRef;

    @Size(max = 5)
    private String serviceCode;

    @NotNull
    private Boolean active;


    private Long partnerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerRef() {
        return customerRef;
    }

    public void setCustomerRef(String customerRef) {
        this.customerRef = customerRef;
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

    public Long getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ServiceIntegrationDTO serviceIntegrationDTO = (ServiceIntegrationDTO) o;
        if (serviceIntegrationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceIntegrationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceIntegrationDTO{" +
            "id=" + getId() +
            ", customerRef='" + getCustomerRef() + "'" +
            ", serviceCode='" + getServiceCode() + "'" +
            ", active='" + isActive() + "'" +
            ", partner=" + getPartnerId() +
            "}";
    }
}
