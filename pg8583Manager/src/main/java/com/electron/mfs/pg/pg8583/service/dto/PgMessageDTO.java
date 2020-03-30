package com.electron.mfs.pg.pg8583.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.pg8583.domain.PgMessage} entity.
 */
public class PgMessageDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 15)
    private String code;

    @NotNull
    @Size(max = 50)
    private String label;

    @NotNull
    private Instant messageDate;

    private String comment;

    @NotNull
    private Boolean active;


    private Long pgMessageModelId;

    private Long pgMessageStatusId;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Instant getMessageDate() {
        return messageDate;
    }

    public void setMessageDate(Instant messageDate) {
        this.messageDate = messageDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getPgMessageModelId() {
        return pgMessageModelId;
    }

    public void setPgMessageModelId(Long pgMessageModelId) {
        this.pgMessageModelId = pgMessageModelId;
    }

    public Long getPgMessageStatusId() {
        return pgMessageStatusId;
    }

    public void setPgMessageStatusId(Long pgMessageStatusId) {
        this.pgMessageStatusId = pgMessageStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PgMessageDTO pgMessageDTO = (PgMessageDTO) o;
        if (pgMessageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pgMessageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PgMessageDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", messageDate='" + getMessageDate() + "'" +
            ", comment='" + getComment() + "'" +
            ", active='" + isActive() + "'" +
            ", pgMessageModel=" + getPgMessageModelId() +
            ", pgMessageStatus=" + getPgMessageStatusId() +
            "}";
    }
}
