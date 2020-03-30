package com.electron.mfs.pg.mdm.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.mdm.domain.OperationType} entity.
 */
public class OperationTypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String code;

    @NotNull
    private Boolean active;

    @NotNull
    @Size(max = 100)
    private String label;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OperationTypeDTO operationTypeDTO = (OperationTypeDTO) o;
        if (operationTypeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), operationTypeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OperationTypeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", active='" + isActive() + "'" +
            ", label='" + getLabel() + "'" +
            "}";
    }
}
