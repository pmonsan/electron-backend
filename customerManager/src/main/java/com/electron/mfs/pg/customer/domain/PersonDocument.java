package com.electron.mfs.pg.customer.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PersonDocument.
 */
@Entity
@Table(name = "t_person_document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PersonDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(name = "document_number", length = 50)
    private String documentNumber;

    @Size(max = 10)
    @Column(name = "expiration_date", length = 10)
    private String expirationDate;

    @NotNull
    @Column(name = "is_valid", nullable = false)
    private Boolean isValid;

    @NotNull
    @Size(max = 5)
    @Column(name = "document_type_code", length = 5, nullable = false)
    private String documentTypeCode;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("personDocuments")
    private Customer customer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public PersonDocument documentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
        return this;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public PersonDocument expirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
        return this;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean isIsValid() {
        return isValid;
    }

    public PersonDocument isValid(Boolean isValid) {
        this.isValid = isValid;
        return this;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public String getDocumentTypeCode() {
        return documentTypeCode;
    }

    public PersonDocument documentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
        return this;
    }

    public void setDocumentTypeCode(String documentTypeCode) {
        this.documentTypeCode = documentTypeCode;
    }

    public Boolean isActive() {
        return active;
    }

    public PersonDocument active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Customer getCustomer() {
        return customer;
    }

    public PersonDocument customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonDocument)) {
            return false;
        }
        return id != null && id.equals(((PersonDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PersonDocument{" +
            "id=" + getId() +
            ", documentNumber='" + getDocumentNumber() + "'" +
            ", expirationDate='" + getExpirationDate() + "'" +
            ", isValid='" + isIsValid() + "'" +
            ", documentTypeCode='" + getDocumentTypeCode() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
