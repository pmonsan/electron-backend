import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IConnectorType, ConnectorType } from 'app/shared/model/connector-type.model';
import { ConnectorTypeService } from './connector-type.service';

@Component({
  selector: 'jhi-connector-type-update',
  templateUrl: './connector-type-update.component.html'
})
export class ConnectorTypeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    description: [null, [Validators.maxLength(255)]],
    active: [null, [Validators.required]]
  });

  constructor(protected connectorTypeService: ConnectorTypeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ connectorType }) => {
      this.updateForm(connectorType);
    });
  }

  updateForm(connectorType: IConnectorType) {
    this.editForm.patchValue({
      id: connectorType.id,
      label: connectorType.label,
      description: connectorType.description,
      active: connectorType.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const connectorType = this.createFromForm();
    if (connectorType.id !== undefined) {
      this.subscribeToSaveResponse(this.connectorTypeService.update(connectorType));
    } else {
      this.subscribeToSaveResponse(this.connectorTypeService.create(connectorType));
    }
  }

  private createFromForm(): IConnectorType {
    return {
      ...new ConnectorType(),
      id: this.editForm.get(['id']).value,
      label: this.editForm.get(['label']).value,
      description: this.editForm.get(['description']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IConnectorType>>) {
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
