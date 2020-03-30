import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ILoanInstalment, LoanInstalment } from 'app/shared/model/loan-instalment.model';
import { LoanInstalmentService } from './loan-instalment.service';
import { ITransaction } from 'app/shared/model/transaction.model';
import { TransactionService } from 'app/entities/transaction';

@Component({
  selector: 'jhi-loan-instalment-update',
  templateUrl: './loan-instalment-update.component.html'
})
export class LoanInstalmentUpdateComponent implements OnInit {
  isSaving: boolean;

  transactions: ITransaction[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(10)]],
    expectedPaymentDate: [null, [Validators.required]],
    realPaymentDate: [],
    amountToPay: [null, [Validators.required]],
    penalityFee: [],
    statusDate: [],
    loanInstalmentStatusCode: [null, [Validators.required, Validators.maxLength(5)]],
    active: [null, [Validators.required]],
    loanId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected loanInstalmentService: LoanInstalmentService,
    protected transactionService: TransactionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ loanInstalment }) => {
      this.updateForm(loanInstalment);
    });
    this.transactionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITransaction[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransaction[]>) => response.body)
      )
      .subscribe((res: ITransaction[]) => (this.transactions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(loanInstalment: ILoanInstalment) {
    this.editForm.patchValue({
      id: loanInstalment.id,
      code: loanInstalment.code,
      expectedPaymentDate: loanInstalment.expectedPaymentDate != null ? loanInstalment.expectedPaymentDate.format(DATE_TIME_FORMAT) : null,
      realPaymentDate: loanInstalment.realPaymentDate != null ? loanInstalment.realPaymentDate.format(DATE_TIME_FORMAT) : null,
      amountToPay: loanInstalment.amountToPay,
      penalityFee: loanInstalment.penalityFee,
      statusDate: loanInstalment.statusDate != null ? loanInstalment.statusDate.format(DATE_TIME_FORMAT) : null,
      loanInstalmentStatusCode: loanInstalment.loanInstalmentStatusCode,
      active: loanInstalment.active,
      loanId: loanInstalment.loanId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const loanInstalment = this.createFromForm();
    if (loanInstalment.id !== undefined) {
      this.subscribeToSaveResponse(this.loanInstalmentService.update(loanInstalment));
    } else {
      this.subscribeToSaveResponse(this.loanInstalmentService.create(loanInstalment));
    }
  }

  private createFromForm(): ILoanInstalment {
    return {
      ...new LoanInstalment(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      expectedPaymentDate:
        this.editForm.get(['expectedPaymentDate']).value != null
          ? moment(this.editForm.get(['expectedPaymentDate']).value, DATE_TIME_FORMAT)
          : undefined,
      realPaymentDate:
        this.editForm.get(['realPaymentDate']).value != null
          ? moment(this.editForm.get(['realPaymentDate']).value, DATE_TIME_FORMAT)
          : undefined,
      amountToPay: this.editForm.get(['amountToPay']).value,
      penalityFee: this.editForm.get(['penalityFee']).value,
      statusDate:
        this.editForm.get(['statusDate']).value != null ? moment(this.editForm.get(['statusDate']).value, DATE_TIME_FORMAT) : undefined,
      loanInstalmentStatusCode: this.editForm.get(['loanInstalmentStatusCode']).value,
      active: this.editForm.get(['active']).value,
      loanId: this.editForm.get(['loanId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanInstalment>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTransactionById(index: number, item: ITransaction) {
    return item.id;
  }
}
