import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ITransaction, Transaction } from 'app/shared/model/transaction.model';
import { TransactionService } from './transaction.service';

@Component({
  selector: 'jhi-transaction-update',
  templateUrl: './transaction-update.component.html'
})
export class TransactionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    transactionNumber: [null, [Validators.required, Validators.maxLength(50)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    transactionAmount: [null, [Validators.required, Validators.min(0)]],
    transactionDate: [null, [Validators.required]],
    feesSupported: [null, [Validators.required]],
    transactionFees: [null, [Validators.min(0)]],
    priceCode: [null, [Validators.required, Validators.maxLength(10)]],
    fromPartnerCode: [null, [Validators.required, Validators.maxLength(5)]],
    toPartnerCode: [null, [Validators.required, Validators.maxLength(5)]],
    transactionStatusCode: [null, [Validators.required, Validators.maxLength(5)]],
    transactionTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    serviceCode: [null, [Validators.required, Validators.maxLength(5)]],
    comment: [null, [Validators.maxLength(255)]],
    active: [null, [Validators.required]]
  });

  constructor(protected transactionService: TransactionService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transaction }) => {
      this.updateForm(transaction);
    });
  }

  updateForm(transaction: ITransaction) {
    this.editForm.patchValue({
      id: transaction.id,
      transactionNumber: transaction.transactionNumber,
      label: transaction.label,
      transactionAmount: transaction.transactionAmount,
      transactionDate: transaction.transactionDate != null ? transaction.transactionDate.format(DATE_TIME_FORMAT) : null,
      feesSupported: transaction.feesSupported,
      transactionFees: transaction.transactionFees,
      priceCode: transaction.priceCode,
      fromPartnerCode: transaction.fromPartnerCode,
      toPartnerCode: transaction.toPartnerCode,
      transactionStatusCode: transaction.transactionStatusCode,
      transactionTypeCode: transaction.transactionTypeCode,
      serviceCode: transaction.serviceCode,
      comment: transaction.comment,
      active: transaction.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transaction = this.createFromForm();
    if (transaction.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionService.update(transaction));
    } else {
      this.subscribeToSaveResponse(this.transactionService.create(transaction));
    }
  }

  private createFromForm(): ITransaction {
    return {
      ...new Transaction(),
      id: this.editForm.get(['id']).value,
      transactionNumber: this.editForm.get(['transactionNumber']).value,
      label: this.editForm.get(['label']).value,
      transactionAmount: this.editForm.get(['transactionAmount']).value,
      transactionDate:
        this.editForm.get(['transactionDate']).value != null
          ? moment(this.editForm.get(['transactionDate']).value, DATE_TIME_FORMAT)
          : undefined,
      feesSupported: this.editForm.get(['feesSupported']).value,
      transactionFees: this.editForm.get(['transactionFees']).value,
      priceCode: this.editForm.get(['priceCode']).value,
      fromPartnerCode: this.editForm.get(['fromPartnerCode']).value,
      toPartnerCode: this.editForm.get(['toPartnerCode']).value,
      transactionStatusCode: this.editForm.get(['transactionStatusCode']).value,
      transactionTypeCode: this.editForm.get(['transactionTypeCode']).value,
      serviceCode: this.editForm.get(['serviceCode']).value,
      comment: this.editForm.get(['comment']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransaction>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
