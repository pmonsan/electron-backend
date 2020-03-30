package com.electron.mfs.pg.gateway.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.electron.mfs.pg.gateway.domain.enumeration.CustomerBlacklistStatus;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.CustomerBlacklist} entity.
 */
public class CustomerBlacklistDTO implements Serializable {

    private Long id;

    @NotNull
    private CustomerBlacklistStatus customerBlacklistStatus;

    @NotNull
    private Instant insertionDate;

    private Instant modificationDate;

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

    public CustomerBlacklistStatus getCustomerBlacklistStatus() {
        return customerBlacklistStatus;
    }

    public void setCustomerBlacklistStatus(CustomerBlacklistStatus customerBlacklistStatus) {
        this.customerBlacklistStatus = customerBlacklistStatus;
    }

    public Instant getInsertionDate() {
        return insertionDate;
    }

    public void setInsertionDate(Instant insertionDate) {
        this.insertionDate = insertionDate;
    }

    public Instant getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Instant modificationDate) {
        this.modificationDate = modificationDate;
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

        CustomerBlacklistDTO customerBlacklistDTO = (CustomerBlacklistDTO) o;
        if (customerBlacklistDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerBlacklistDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerBlacklistDTO{" +
            "id=" + getId() +
            ", customerBlacklistStatus='" + getCustomerBlacklistStatus() + "'" +
            ", insertionDate='" + getInsertionDate() + "'" +
            ", modificationDate='" + getModificationDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", active='" + isActive() + "'" +
            ", customer=" + getCustomerId() +
            "}";
    }
}
