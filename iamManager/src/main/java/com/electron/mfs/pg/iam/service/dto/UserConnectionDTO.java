package com.electron.mfs.pg.iam.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.iam.domain.UserConnection} entity.
 */
public class UserConnectionDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant loginDate;

    private Instant logoutDate;


    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Instant loginDate) {
        this.loginDate = loginDate;
    }

    public Instant getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(Instant logoutDate) {
        this.logoutDate = logoutDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long pgUserId) {
        this.userId = pgUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserConnectionDTO userConnectionDTO = (UserConnectionDTO) o;
        if (userConnectionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userConnectionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserConnectionDTO{" +
            "id=" + getId() +
            ", loginDate='" + getLoginDate() + "'" +
            ", logoutDate='" + getLogoutDate() + "'" +
            ", user=" + getUserId() +
            "}";
    }
}
