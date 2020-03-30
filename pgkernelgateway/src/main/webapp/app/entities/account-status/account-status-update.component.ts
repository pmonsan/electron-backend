import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAccountStatus, AccountStatus } from 'app/shared/model/account-status.model';
import { AccountStatusService } from './account-status.service';

@Component({
  selector: 'jhi-account-status-update',
  templateUrl: './account-status-update.component.html'
})
export class AccountStatusUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(protected accountStatusService: AccountStatusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ accountStatus }) => {
      this.updateForm(accountStatus);
    });
  }

  updateForm(accountStatus: IAccountStatus) {
    this.editForm.patchValue({
      id: accountStatus.id,
      code: accountStatus.code,
      label: accountStatus.label,
      active: accountStatus.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const accountStatus = this.createFromForm();
    if (accountStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.accountStatusService.update(accountStatus));
    } else {
      this.subscribeToSaveResponse(this.accountStatusService.create(accountStatus));
    }
  }

  private createFromForm(): IAccountStatus {
    return {
      ...new AccountStatus(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountStatus>>) {
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
