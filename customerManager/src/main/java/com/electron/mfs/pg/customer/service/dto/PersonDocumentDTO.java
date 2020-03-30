package com.electron.mfs.pg.customer.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.customer.domain.PersonDocument} entity.
 */
public class PersonDocumentDTO implements Serializable {

    private Long id;

    @Size(max = 50)
    private String documentNumber;

    @Size(max = 10)
    private String expirationDate;

    @NotNull
    private Boolean isValid;

    @NotNull
    @Size(max = 5)
    private String documentTypeCode;

    @NotNull
    private Boolean active;


    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean isIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getDocumentTypeCode() {
        return documentTypeCode;
    }

    public void setDocumentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
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

        PersonDocumentDTO personDocumentDTO = (PersonDocumentDTO) o;
        if (personDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), personDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PersonDocumentDTO{" +
            "id=" + getId() +
            ", documentNumber='" + getDocumentNumber() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", isValid='" + isIsValid() + "'" +
            ", documentTypeCode='" + getDocumentTypeCode() + "'" +
            ", active='" + isActive() + "'" +
            ", customer=" + getCustomerId() +
            "}";
    }
}
