package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A UserConnection.
 */
@Entity
@Table(name = "t_user_connection")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UserConnection implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "login_date", nullable = false)
    private Instant loginDate;

    @Column(name = "logout_date")
    private Instant logoutDate;

    @ManyToOne
    @JsonIgnoreProperties("userConnections")
    private PgUser user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getLoginDate() {
        return loginDate;
    }

    public UserConnection loginDate(Instant loginDate) {
        this.loginDate = loginDate;
        return this;
    }

    public void setLoginDate(Instant loginDate) {
        this.loginDate = loginDate;
    }

    public Instant getLogoutDate() {
        return logoutDate;
    }

    public UserConnection logoutDate(Instant logoutDate) {
        this.logoutDate = logoutDate;
        return this;
    }

    public void setLogoutDate(Instant logoutDate) {
        this.logoutDate = logoutDate;
    }

    public PgUser getUser() {
        return user;
    }

    public UserConnection user(PgUser pgUser) {
        this.user = pgUser;
        return this;
    }

    public void setUser(PgUser pgUser) {
        this.user = pgUser;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserConnection)) {
            return false;
        }
        return id != null && id.equals(((UserConnection) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UserConnection{" +
            "id=" + getId() +
            ", loginDate='" + getLoginDate() + "'" +
            ", logoutDate='" + getLogoutDate() + "'" +
            "}";
    }
}
