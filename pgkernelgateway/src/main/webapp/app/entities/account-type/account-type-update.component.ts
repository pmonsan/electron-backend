import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IAccountType, AccountType } from 'app/shared/model/account-type.model';
import { AccountTypeService } from './account-type.service';

@Component({
  selector: 'jhi-account-type-update',
  templateUrl: './account-type-update.component.html'
})
export class AccountTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(protected accountTypeService: AccountTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ accountType }) => {
      this.updateForm(accountType);
    });
  }

  updateForm(accountType: IAccountType) {
    this.editForm.patchValue({
      id: accountType.id,
      code: accountType.code,
      label: accountType.label,
      active: accountType.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const accountType = this.createFromForm();
    if (accountType.id !== undefined) {
      this.subscribeToSaveResponse(this.accountTypeService.update(accountType));
    } else {
      this.subscribeToSaveResponse(this.accountTypeService.create(accountType));
    }
  }

  private createFromForm(): IAccountType {
    return {
      ...new AccountType(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccountType>>) {
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
