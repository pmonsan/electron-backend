import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITransactionStatus, TransactionStatus } from 'app/shared/model/transaction-status.model';
import { TransactionStatusService } from './transaction-status.service';

@Component({
  selector: 'jhi-transaction-status-update',
  templateUrl: './transaction-status-update.component.html'
})
export class TransactionStatusUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected transactionStatusService: TransactionStatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transactionStatus }) => {
      this.updateForm(transactionStatus);
    });
  }

  updateForm(transactionStatus: ITransactionStatus) {
    this.editForm.patchValue({
      id: transactionStatus.id,
      code: transactionStatus.code,
      label: transactionStatus.label,
      active: transactionStatus.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transactionStatus = this.createFromForm();
    if (transactionStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionStatusService.update(transactionStatus));
    } else {
      this.subscribeToSaveResponse(this.transactionStatusService.create(transactionStatus));
    }
  }

  private createFromForm(): ITransactionStatus {
    return {
      ...new TransactionStatus(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionStatus>>) {
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
