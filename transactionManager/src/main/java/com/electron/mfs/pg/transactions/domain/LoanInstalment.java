package com.electron.mfs.pg.transactions.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A LoanInstalment.
 */
@Entity
@Table(name = "t_loan_instalment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LoanInstalment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @NotNull
    @Column(name = "expected_payment_date", nullable = false)
    private Instant expectedPaymentDate;

    @Column(name = "real_payment_date")
    private Instant realPaymentDate;

    @NotNull
    @Column(name = "amount_to_pay", nullable = false)
    private Double amountToPay;

    @Column(name = "penality_fee")
    private Double penalityFee;

    @Column(name = "status_date")
    private Instant statusDate;

    @NotNull
    @Size(max = 5)
    @Column(name = "loan_instalment_status_code", length = 5, nullable = false)
    private String loanInstalmentStatusCode;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne
    @JsonIgnoreProperties("loanInstalments")
    private Transaction loan;

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

    public LoanInstalment code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getExpectedPaymentDate() {
        return expectedPaymentDate;
    }

    public LoanInstalment expectedPaymentDate(Instant expectedPaymentDate) {
        this.expectedPaymentDate = expectedPaymentDate;
        return this;
    }

    public void setExpectedPaymentDate(Instant expectedPaymentDate) {
        this.expectedPaymentDate = expectedPaymentDate;
    }

    public Instant getRealPaymentDate() {
        return realPaymentDate;
    }

    public LoanInstalment realPaymentDate(Instant realPaymentDate) {
        this.realPaymentDate = realPaymentDate;
        return this;
    }

    public void setRealPaymentDate(Instant realPaymentDate) {
        this.realPaymentDate = realPaymentDate;
    }

    public Double getAmountToPay() {
        return amountToPay;
    }

    public LoanInstalment amountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
        return this;
    }

    public void setAmountToPay(Double amountToPay) {
        this.amountToPay = amountToPay;
    }

    public Double getPenalityFee() {
        return penalityFee;
    }

    public LoanInstalment penalityFee(Double penalityFee) {
        this.penalityFee = penalityFee;
        return this;
    }

    public void setPenalityFee(Double penalityFee) {
        this.penalityFee = penalityFee;
    }

    public Instant getStatusDate() {
        return statusDate;
    }

    public LoanInstalment statusDate(Instant statusDate) {
        this.statusDate = statusDate;
        return this;
    }

    public void setStatusDate(Instant statusDate) {
        this.statusDate = statusDate;
    }

    public String getLoanInstalmentStatusCode() {
        return loanInstalmentStatusCode;
    }

    public LoanInstalment loanInstalmentStatusCode(String loanInstalmentStatusCode) {
        this.loanInstalmentStatusCode = loanInstalmentStatusCode;
        return this;
    }

    public void setLoanInstalmentStatusCode(String loanInstalmentStatusCode) {
        this.loanInstalmentStatusCode = loanInstalmentStatusCode;
    }

    public Boolean isActive() {
        return active;
    }

    public LoanInstalment active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Transaction getLoan() {
        return loan;
    }

    public LoanInstalment loan(Transaction transaction) {
        this.loan = transaction;
        return this;
    }

    public void setLoan(Transaction transaction) {
        this.loan = transaction;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoanInstalment)) {
            return false;
        }
        return id != null && id.equals(((LoanInstalment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "LoanInstalment{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", expectedPaymentDate='" + getExpectedPaymentDate() + "'" +
            ", realPaymentDate='" + getRealPaymentDate() + "'" +
            ", amountToPay=" + getAmountToPay() +
            ", penalityFee=" + getPenalityFee() +
            ", statusDate='" + getStatusDate() + "'" +
            ", loanInstalmentStatusCode='" + getLoanInstalmentStatusCode() + "'" +
            ", active='" + isActive() + "'" +
            "}";
    }
}
