package com.electron.mfs.pg.account.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.account.domain.AccountFeature} entity.
 */
public class AccountFeatureDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant activationDate;

    @NotNull
    @Size(max = 5)
    private String featureCode;

    @NotNull
    private Boolean active;


    private Long accountId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(Instant activationDate) {
        this.activationDate = activationDate;
    }

    public String getFeatureCode() {
        return featureCode;
    }

    public void setFeatureCode(String featureCode) {
        this.featureCode = featureCode;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long pgAccountId) {
        this.accountId = pgAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AccountFeatureDTO accountFeatureDTO = (AccountFeatureDTO) o;
        if (accountFeatureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accountFeatureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AccountFeatureDTO{" +
            "id=" + getId() +
            ", activationDate='" + getActivationDate() + "'" +
            ", featureCode='" + getFeatureCode() + "'" +
            ", active='" + isActive() + "'" +
            ", account=" + getAccountId() +
            "}";
    }
}
