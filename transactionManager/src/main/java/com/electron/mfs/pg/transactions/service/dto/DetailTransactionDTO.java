package com.electron.mfs.pg.transactions.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.transactions.domain.DetailTransaction} entity.
 */
public class DetailTransactionDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String pgDataCode;

    private String dataValue;

    @NotNull
    private Boolean active;


    private Long transactionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPgDataCode() {
        return pgDataCode;
    }

    public void setPgDataCode(String pgDataCode) {
        this.pgDataCode = pgDataCode;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetailTransactionDTO detailTransactionDTO = (DetailTransactionDTO) o;
        if (detailTransactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), detailTransactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DetailTransactionDTO{" +
            "id=" + getId() +
            ", pgDataCode='" + getPgDataCode() + "'" +
            ", dataValue='" + getDataValue() + "'" +
            ", active='" + isActive() + "'" +
            ", transaction=" + getTransactionId() +
            "}";
    }
}
