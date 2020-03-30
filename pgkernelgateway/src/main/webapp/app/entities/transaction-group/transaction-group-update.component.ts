import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITransactionGroup, TransactionGroup } from 'app/shared/model/transaction-group.model';
import { TransactionGroupService } from './transaction-group.service';

@Component({
  selector: 'jhi-transaction-group-update',
  templateUrl: './transaction-group-update.component.html'
})
export class TransactionGroupUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    checkSubscription: [null, [Validators.required]],
    ignoreFees: [null, [Validators.required]],
    ignoreLimit: [null, [Validators.required]],
    ignoreCommission: [null, [Validators.required]],
    checkOtp: [null, [Validators.required]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected transactionGroupService: TransactionGroupService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transactionGroup }) => {
      this.updateForm(transactionGroup);
    });
  }

  updateForm(transactionGroup: ITransactionGroup) {
    this.editForm.patchValue({
      id: transactionGroup.id,
      code: transactionGroup.code,
      label: transactionGroup.label,
      checkSubscription: transactionGroup.checkSubscription,
      ignoreFees: transactionGroup.ignoreFees,
      ignoreLimit: transactionGroup.ignoreLimit,
      ignoreCommission: transactionGroup.ignoreCommission,
      checkOtp: transactionGroup.checkOtp,
      active: transactionGroup.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transactionGroup = this.createFromForm();
    if (transactionGroup.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionGroupService.update(transactionGroup));
    } else {
      this.subscribeToSaveResponse(this.transactionGroupService.create(transactionGroup));
    }
  }

  private createFromForm(): ITransactionGroup {
    return {
      ...new TransactionGroup(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      checkSubscription: this.editForm.get(['checkSubscription']).value,
      ignoreFees: this.editForm.get(['ignoreFees']).value,
      ignoreLimit: this.editForm.get(['ignoreLimit']).value,
      ignoreCommission: this.editForm.get(['ignoreCommission']).value,
      checkOtp: this.editForm.get(['checkOtp']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionGroup>>) {
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
