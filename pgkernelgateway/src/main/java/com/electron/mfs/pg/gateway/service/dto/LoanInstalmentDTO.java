package com.electron.mfs.pg.gateway.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.electron.mfs.pg.gateway.domain.LoanInstalment} entity.
 */
public class LoanInstalmentDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String code;

    @NotNull
    private Instant expectedPaymentDate;

    private Instant realPaymentDate;

    @NotNull
    private Double amountToPay;

    private Double penalityFee;

    private Instant statusDate;

    @NotNull
    @Size(max = 5)
    private String loanInstalmentStatusCode;

    @NotNull
    private Boolean active;


    private Long loanId;

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

    public Instant getExpectedPaymentDate() {
        return expectedPaymentDate;
    }

    public void setExpectedPaymentDate(Instant expectedPaymentDate) {
        this.expectedPaymentDate = expectedPaymentDate;
    }

    public Instant getRealPaymentDate() {
        return realPaymentDate;
    }

    public void setRealPaymentDate(Instant realPaymentDate) {
        this.realPaymentDate = realPaymentDate;
    }

    public Double getAmountToPay() {
        return amountToPay;
    }

    public void setAmountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public Double getPenalityFee() {
        return penalityFee;
    }

    public void setPenalityFee(Double penalityFee) {
        this.penalityFee = penalityFee;
    }

    public Instant getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Instant statusDate) {
        this.statusDate = statusDate;
    }

    public String getLoanInstalmentStatusCode() {
        return loanInstalmentStatusCode;
    }

    public void setLoanInstalmentStatusCode(String loanInstalmentStatusCode) {
        this.loanInstalmentStatusCode = loanInstalmentStatusCode;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getLoanId() {
        return loanId;
    }

    public void setLoanId(Long transactionId) {
        this.loanId = transactionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoanInstalmentDTO loanInstalmentDTO = (LoanInstalmentDTO) o;
        if (loanInstalmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), loanInstalmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LoanInstalmentDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", expectedPaymentDate='" + getExpectedPaymentDate() + "'" +
            ", realPaymentDate='" + getRealPaymentDate() + "'" +
            ", amountToPay=" + getAmountToPay() +
            ", penalityFee=" + getPenalityFee() +
            ", statusDate='" + getStatusDate() + "'" +
            ", loanInstalmentStatusCode='" + getLoanInstalmentStatusCode() + "'" +
            ", active='" + isActive() + "'" +
            ", loan=" + getLoanId() +
            "}";
    }
}
