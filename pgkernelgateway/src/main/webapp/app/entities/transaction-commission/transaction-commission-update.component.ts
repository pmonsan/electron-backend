import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ITransactionCommission, TransactionCommission } from 'app/shared/model/transaction-commission.model';
import { TransactionCommissionService } from './transaction-commission.service';
import { ITransaction } from 'app/shared/model/transaction.model';
import { TransactionService } from 'app/entities/transaction';

@Component({
  selector: 'jhi-transaction-commission-update',
  templateUrl: './transaction-commission-update.component.html'
})
export class TransactionCommissionUpdateComponent implements OnInit {
  isSaving: boolean;

  transactions: ITransaction[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(10)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    amount: [null, [Validators.min(0)]],
    amountInPercent: [null, [Validators.required]],
    dateCreation: [null, [Validators.required]],
    percent: [null, [Validators.min(0)]],
    currencyCode: [null, [Validators.required, Validators.maxLength(5)]],
    partnerCode: [null, [Validators.required, Validators.maxLength(5)]],
    serviceCode: [null, [Validators.required, Validators.maxLength(5)]],
    description: [null, [Validators.maxLength(255)]],
    commission: [null, [Validators.min(0)]],
    transactionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected transactionCommissionService: TransactionCommissionService,
    protected transactionService: TransactionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transactionCommission }) => {
      this.updateForm(transactionCommission);
    });
    this.transactionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITransaction[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransaction[]>) => response.body)
      )
      .subscribe((res: ITransaction[]) => (this.transactions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(transactionCommission: ITransactionCommission) {
    this.editForm.patchValue({
      id: transactionCommission.id,
      code: transactionCommission.code,
      label: transactionCommission.label,
      amount: transactionCommission.amount,
      amountInPercent: transactionCommission.amountInPercent,
      dateCreation: transactionCommission.dateCreation != null ? transactionCommission.dateCreation.format(DATE_TIME_FORMAT) : null,
      percent: transactionCommission.percent,
      currencyCode: transactionCommission.currencyCode,
      partnerCode: transactionCommission.partnerCode,
      serviceCode: transactionCommission.serviceCode,
      description: transactionCommission.description,
      commission: transactionCommission.commission,
      transactionId: transactionCommission.transactionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transactionCommission = this.createFromForm();
    if (transactionCommission.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionCommissionService.update(transactionCommission));
    } else {
      this.subscribeToSaveResponse(this.transactionCommissionService.create(transactionCommission));
    }
  }

  private createFromForm(): ITransactionCommission {
    return {
      ...new TransactionCommission(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      amount: this.editForm.get(['amount']).value,
      amountInPercent: this.editForm.get(['amountInPercent']).value,
      dateCreation:
        this.editForm.get(['dateCreation']).value != null ? moment(this.editForm.get(['dateCreation']).value, DATE_TIME_FORMAT) : undefined,
      percent: this.editForm.get(['percent']).value,
      currencyCode: this.editForm.get(['currencyCode']).value,
      partnerCode: this.editForm.get(['partnerCode']).value,
      serviceCode: this.editForm.get(['serviceCode']).value,
      description: this.editForm.get(['description']).value,
      commission: this.editForm.get(['commission']).value,
      transactionId: this.editForm.get(['transactionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionCommission>>) {
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
