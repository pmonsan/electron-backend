package com.electron.mfs.pg.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A PgMessage.
 */
@Entity
@Table(name = "t_pg_message")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PgMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 15)
    @Column(name = "code", length = 15, nullable = false)
    private String code;

    @NotNull
    @Size(max = 50)
    @Column(name = "label", length = 50, nullable = false)
    private String label;

    @NotNull
    @Column(name = "message_date", nullable = false)
    private Instant messageDate;

    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("pgMessages")
    private PgMessageModel pgMessageModel;

    @ManyToOne
    @JsonIgnoreProperties("pgMessages")
    private PgMessageStatus pgMessageStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public PgMessage code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public PgMessage label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Instant getMessageDate() {
        return messageDate;
    }

    public PgMessage messageDate(Instant messageDate) {
        this.messageDate = messageDate;
        return this;
    }

    public void setMessageDate(Instant messageDate) {
        this.messageDate = messageDate;
    }

    public String getComment() {
        return comment;
    }

    public PgMessage comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isActive() {
        return active;
    }

    public PgMessage active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public PgMessageModel getPgMessageModel() {
        return pgMessageModel;
    }

    public PgMessage pgMessageModel(PgMessageModel pgMessageModel) {
        this.pgMessageModel = pgMessageModel;
        return this;
    }

    public void setPgMessageModel(PgMessageModel pgMessageModel) {
        this.pgMessageModel = pgMessageModel;
    }

    public PgMessageStatus getPgMessageStatus() {
        return pgMessageStatus;
    }

    public PgMessage pgMessageStatus(PgMessageStatus pgMessageStatus) {
        this.pgMessageStatus = pgMessageStatus;
        return this;
    }

    public void setPgMessageStatus(PgMessageStatus pgMessageStatus) {
        this.pgMessageStatus = pgMessageStatus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PgMessage)) {
            return false;
        }
        return id != null && id.equals(((PgMessage) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "PgMessage{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", messageDate='" + getMessageDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
