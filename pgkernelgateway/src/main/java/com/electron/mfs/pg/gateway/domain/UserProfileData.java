package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A UserProfileData.
 */
@Entity
@Table(name = "t_user_profile_data")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserProfileData implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Size(max = 5)
    @Column(name = "pg_data_code", length = 5)
    private String pgDataCode;

    @ManyToOne
    @JsonIgnoreProperties("userProfileData")
    private UserProfile userProfile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public UserProfileData active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPgDataCode() {
        return pgDataCode;
    }

    public UserProfileData pgDataCode(String pgDataCode) {
        this.pgDataCode = pgDataCode;
        return this;
    }

    public void setPgDataCode(String pgDataCode) {
        this.pgDataCode = pgDataCode;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public UserProfileData userProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserProfileData)) {
            return false;
        }
        return id != null && id.equals(((UserProfileData) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserProfileData{" +
            "id=" + getId() +
            ", active='" + isActive() + "'" +
            ", pgDataCode='" + getPgDataCode() + "'" +
            "}";
    }
}
