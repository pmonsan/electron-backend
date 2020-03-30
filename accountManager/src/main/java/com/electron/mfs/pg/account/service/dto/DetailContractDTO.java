package com.electron.mfs.pg.account.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.account.domain.DetailContract} entity.
 */
public class DetailContractDTO implements Serializable {

    private Long id;

    @Size(max = 255)
    private String comment;

    @NotNull
    private Boolean active;


    private Long contractId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetailContractDTO detailContractDTO = (DetailContractDTO) o;
        if (detailContractDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detailContractDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetailContractDTO{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", active='" + isActive() + "'" +
            ", contract=" + getContractId() +
            "}";
    }
}
