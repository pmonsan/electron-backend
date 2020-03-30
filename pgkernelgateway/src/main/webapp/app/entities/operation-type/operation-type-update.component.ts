import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IOperationType, OperationType } from 'app/shared/model/operation-type.model';
import { OperationTypeService } from './operation-type.service';

@Component({
  selector: 'jhi-operation-type-update',
  templateUrl: './operation-type-update.component.html'
})
export class OperationTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    active: [null, [Validators.required]],
    label: [null, [Validators.required, Validators.maxLength(100)]]
  });

  constructor(protected operationTypeService: OperationTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ operationType }) => {
      this.updateForm(operationType);
    });
  }

  updateForm(operationType: IOperationType) {
    this.editForm.patchValue({
      id: operationType.id,
      code: operationType.code,
      active: operationType.active,
      label: operationType.label
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const operationType = this.createFromForm();
    if (operationType.id !== undefined) {
      this.subscribeToSaveResponse(this.operationTypeService.update(operationType));
    } else {
      this.subscribeToSaveResponse(this.operationTypeService.create(operationType));
    }
  }

  private createFromForm(): IOperationType {
    return {
      ...new OperationType(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      active: this.editForm.get(['active']).value,
      label: this.editForm.get(['label']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOperationType>>) {
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
