import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ILoanInstalmentStatus, LoanInstalmentStatus } from 'app/shared/model/loan-instalment-status.model';
import { LoanInstalmentStatusService } from './loan-instalment-status.service';

@Component({
  selector: 'jhi-loan-instalment-status-update',
  templateUrl: './loan-instalment-status-update.component.html'
})
export class LoanInstalmentStatusUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected loanInstalmentStatusService: LoanInstalmentStatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ loanInstalmentStatus }) => {
      this.updateForm(loanInstalmentStatus);
    });
  }

  updateForm(loanInstalmentStatus: ILoanInstalmentStatus) {
    this.editForm.patchValue({
      id: loanInstalmentStatus.id,
      code: loanInstalmentStatus.code,
      label: loanInstalmentStatus.label,
      active: loanInstalmentStatus.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const loanInstalmentStatus = this.createFromForm();
    if (loanInstalmentStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.loanInstalmentStatusService.update(loanInstalmentStatus));
    } else {
      this.subscribeToSaveResponse(this.loanInstalmentStatusService.create(loanInstalmentStatus));
    }
  }

  private createFromForm(): ILoanInstalmentStatus {
    return {
      ...new LoanInstalmentStatus(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILoanInstalmentStatus>>) {
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
