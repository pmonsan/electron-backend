import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IOperationStatus, OperationStatus } from 'app/shared/model/operation-status.model';
import { OperationStatusService } from './operation-status.service';

@Component({
  selector: 'jhi-operation-status-update',
  templateUrl: './operation-status-update.component.html'
})
export class OperationStatusUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected operationStatusService: OperationStatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ operationStatus }) => {
      this.updateForm(operationStatus);
    });
  }

  updateForm(operationStatus: IOperationStatus) {
    this.editForm.patchValue({
      id: operationStatus.id,
      code: operationStatus.code,
      label: operationStatus.label,
      active: operationStatus.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const operationStatus = this.createFromForm();
    if (operationStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.operationStatusService.update(operationStatus));
    } else {
      this.subscribeToSaveResponse(this.operationStatusService.create(operationStatus));
    }
  }

  private createFromForm(): IOperationStatus {
    return {
      ...new OperationStatus(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperationStatus>>) {
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
