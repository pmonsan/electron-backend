package com.electron.mfs.pg.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.UserProfileData} entity.
 */
public class UserProfileDataDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean active;

    @Size(max = 5)
    private String pgDataCode;


    private Long userProfileId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPgDataCode() {
        return pgDataCode;
    }

    public void setPgDataCode(String pgDataCode) {
        this.pgDataCode = pgDataCode;
    }

    public Long getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserProfileDataDTO userProfileDataDTO = (UserProfileDataDTO) o;
        if (userProfileDataDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userProfileDataDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserProfileDataDTO{" +
            "id=" + getId() +
            ", active='" + isActive() + "'" +
            ", pgDataCode='" + getPgDataCode() + "'" +
            ", userProfile=" + getUserProfileId() +
            "}";
    }
}
