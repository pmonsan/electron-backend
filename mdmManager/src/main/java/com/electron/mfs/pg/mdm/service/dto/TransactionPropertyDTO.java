package com.electron.mfs.pg.mdm.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.electron.mfs.pg.mdm.domain.enumeration.PropertyType;

/**
 * A DTO for the {@link com.electron.mfs.pg.mdm.domain.TransactionProperty} entity.
 */
public class TransactionPropertyDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String code;

    @NotNull
    @Size(max = 100)
    private String label;

    @NotNull
    private PropertyType propertyType;

    @NotNull
    private Boolean active;


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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
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

        TransactionPropertyDTO transactionPropertyDTO = (TransactionPropertyDTO) o;
        if (transactionPropertyDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), transactionPropertyDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TransactionPropertyDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", propertyType='" + getPropertyType() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
