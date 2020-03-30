import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IInternalConnectorStatus, InternalConnectorStatus } from 'app/shared/model/internal-connector-status.model';
import { InternalConnectorStatusService } from './internal-connector-status.service';

@Component({
  selector: 'jhi-internal-connector-status-update',
  templateUrl: './internal-connector-status-update.component.html'
})
export class InternalConnectorStatusUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    status: [null, [Validators.required, Validators.maxLength(100)]],
    defaultReason: [null, [Validators.maxLength(100)]]
  });

  constructor(
    protected internalConnectorStatusService: InternalConnectorStatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ internalConnectorStatus }) => {
      this.updateForm(internalConnectorStatus);
    });
  }

  updateForm(internalConnectorStatus: IInternalConnectorStatus) {
    this.editForm.patchValue({
      id: internalConnectorStatus.id,
      status: internalConnectorStatus.status,
      defaultReason: internalConnectorStatus.defaultReason
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const internalConnectorStatus = this.createFromForm();
    if (internalConnectorStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.internalConnectorStatusService.update(internalConnectorStatus));
    } else {
      this.subscribeToSaveResponse(this.internalConnectorStatusService.create(internalConnectorStatus));
    }
  }

  private createFromForm(): IInternalConnectorStatus {
    return {
      ...new InternalConnectorStatus(),
      id: this.editForm.get(['id']).value,
      status: this.editForm.get(['status']).value,
      defaultReason: this.editForm.get(['defaultReason']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IInternalConnectorStatus>>) {
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
