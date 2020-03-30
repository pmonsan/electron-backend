package com.electron.mfs.pg.mdm.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.mdm.domain.Forex} entity.
 */
public class ForexDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String code;

    private Double rate;

    private Instant creationDate;

    @NotNull
    private Boolean active;


    private Long fromCurrencyId;

    private Long toCurrencyId;

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

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getFromCurrencyId() {
        return fromCurrencyId;
    }

    public void setFromCurrencyId(Long currencyId) {
        this.fromCurrencyId = currencyId;
    }

    public Long getToCurrencyId() {
        return toCurrencyId;
    }

    public void setToCurrencyId(Long currencyId) {
        this.toCurrencyId = currencyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ForexDTO forexDTO = (ForexDTO) o;
        if (forexDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), forexDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ForexDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", rate=" + getRate() +
            ", creationDate='" + getCreationDate() + "'" +
            ", active='" + isActive() + "'" +
            ", fromCurrency=" + getFromCurrencyId() +
            ", toCurrency=" + getToCurrencyId() +
            "}";
    }
}
