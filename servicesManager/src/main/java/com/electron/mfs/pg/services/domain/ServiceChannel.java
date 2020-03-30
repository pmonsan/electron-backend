package com.electron.mfs.pg.services.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A ServiceChannel.
 */
@Entity
@Table(name = "t_service_channel")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServiceChannel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 5)
    @Column(name = "channel_code", length = 5, nullable = false)
    private String channelCode;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("serviceChannels")
    private PgService pgService;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public ServiceChannel channelCode(String channelCode) {
        this.channelCode = channelCode;
        return this;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public Boolean isActive() {
        return active;
    }

    public ServiceChannel active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public PgService getPgService() {
        return pgService;
    }

    public ServiceChannel pgService(PgService pgService) {
        this.pgService = pgService;
        return this;
    }

    public void setPgService(PgService pgService) {
        this.pgService = pgService;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceChannel)) {
            return false;
        }
        return id != null && id.equals(((ServiceChannel) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceChannel{" +
            "id=" + getId() +
            ", channelCode='" + getChannelCode() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
