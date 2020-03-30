import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITransactionType, TransactionType } from 'app/shared/model/transaction-type.model';
import { TransactionTypeService } from './transaction-type.service';

@Component({
  selector: 'jhi-transaction-type-update',
  templateUrl: './transaction-type-update.component.html'
})
export class TransactionTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    useTransactionGroup: [null, [Validators.required]],
    checkSubscription: [null, [Validators.required]],
    ignoreFees: [null, [Validators.required]],
    ignoreLimit: [null, [Validators.required]],
    ignoreCommission: [null, [Validators.required]],
    checkOtp: [null, [Validators.required]],
    pgMessageModelCode: [null, [Validators.required, Validators.maxLength(5)]],
    transactionGroupCode: [null, [Validators.required, Validators.maxLength(5)]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected transactionTypeService: TransactionTypeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transactionType }) => {
      this.updateForm(transactionType);
    });
  }

  updateForm(transactionType: ITransactionType) {
    this.editForm.patchValue({
      id: transactionType.id,
      code: transactionType.code,
      label: transactionType.label,
      useTransactionGroup: transactionType.useTransactionGroup,
      checkSubscription: transactionType.checkSubscription,
      ignoreFees: transactionType.ignoreFees,
      ignoreLimit: transactionType.ignoreLimit,
      ignoreCommission: transactionType.ignoreCommission,
      checkOtp: transactionType.checkOtp,
      pgMessageModelCode: transactionType.pgMessageModelCode,
      transactionGroupCode: transactionType.transactionGroupCode,
      active: transactionType.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transactionType = this.createFromForm();
    if (transactionType.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionTypeService.update(transactionType));
    } else {
      this.subscribeToSaveResponse(this.transactionTypeService.create(transactionType));
    }
  }

  private createFromForm(): ITransactionType {
    return {
      ...new TransactionType(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      useTransactionGroup: this.editForm.get(['useTransactionGroup']).value,
      checkSubscription: this.editForm.get(['checkSubscription']).value,
      ignoreFees: this.editForm.get(['ignoreFees']).value,
      ignoreLimit: this.editForm.get(['ignoreLimit']).value,
      ignoreCommission: this.editForm.get(['ignoreCommission']).value,
      checkOtp: this.editForm.get(['checkOtp']).value,
      pgMessageModelCode: this.editForm.get(['pgMessageModelCode']).value,
      transactionGroupCode: this.editForm.get(['transactionGroupCode']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionType>>) {
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
