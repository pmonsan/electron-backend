package com.electron.mfs.pg.mdm.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.mdm.domain.PgBatch} entity.
 */
public class PgBatchDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 5)
    private String code;

    @Size(max = 100)
    private String label;

    @NotNull
    private Instant expectedEndDate;

    @NotNull
    private Instant batchDate;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Instant getExpectedEndDate() {
        return expectedEndDate;
    }

    public void setExpectedEndDate(Instant expectedEndDate) {
        this.expectedEndDate = expectedEndDate;
    }

    public Instant getBatchDate() {
        return batchDate;
    }

    public void setBatchDate(Instant batchDate) {
        this.batchDate = batchDate;
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

        PgBatchDTO pgBatchDTO = (PgBatchDTO) o;
        if (pgBatchDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), pgBatchDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PgBatchDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", label='" + getLabel() + "'" +
            ", expectedEndDate='" + getExpectedEndDate() + "'" +
            ", batchDate='" + getBatchDate() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
