package com.electron.mfs.pg.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.CustomerLimit} entity.
 */
public class CustomerLimitDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String limitTypeCode;

    @NotNull
    @Size(max = 5)
    private String accountTypeCode;

    @NotNull
    @Size(max = 5)
    private String customerTypeCode;

    private Double value;

    @Size(max = 255)
    private String comment;

    @NotNull
    private Boolean active;


    private Long customerId;

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

    public String getAccountTypeCode() {
        return accountTypeCode;
    }

    public void setAccountTypeCode(String accountTypeCode) {
        this.accountTypeCode = accountTypeCode;
    }

    public String getCustomerTypeCode() {
        return customerTypeCode;
    }

    public void setCustomerTypeCode(String customerTypeCode) {
        this.customerTypeCode = customerTypeCode;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerLimitDTO customerLimitDTO = (CustomerLimitDTO) o;
        if (customerLimitDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerLimitDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerLimitDTO{" +
            "id=" + getId() +
            ", limitTypeCode='" + getLimitTypeCode() + "'" +
            ", accountTypeCode='" + getAccountTypeCode() + "'" +
            ", customerTypeCode='" + getCustomerTypeCode() + "'" +
            ", value=" + getValue() +
            ", comment='" + getComment() + "'" +
            ", active='" + isActive() + "'" +
            ", customer=" + getCustomerId() +
            "}";
    }
}
