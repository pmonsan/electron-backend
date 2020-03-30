package com.electron.mfs.pg.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.Feature} entity.
 */
public class FeatureDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String code;

    @Size(max = 255)
    private String comment;

    @NotNull
    @Size(max = 100)
    private String longLabel;

    @Size(max = 50)
    private String shortLabel;

    @NotNull
    private Boolean active;


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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getLongLabel() {
        return longLabel;
    }

    public void setLongLabel(String longLabel) {
        this.longLabel = longLabel;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public void setShortLabel(String shortLabel) {
        this.shortLabel = shortLabel;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FeatureDTO featureDTO = (FeatureDTO) o;
        if (featureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), featureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FeatureDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", comment='" + getComment() + "'" +
            ", longLabel='" + getLongLabel() + "'" +
            ", shortLabel='" + getShortLabel() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
