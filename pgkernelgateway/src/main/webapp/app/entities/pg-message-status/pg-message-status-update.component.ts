import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPgMessageStatus, PgMessageStatus } from 'app/shared/model/pg-message-status.model';
import { PgMessageStatusService } from './pg-message-status.service';

@Component({
  selector: 'jhi-pg-message-status-update',
  templateUrl: './pg-message-status-update.component.html'
})
export class PgMessageStatusUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required, Validators.maxLength(5)]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected pgMessageStatusService: PgMessageStatusService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgMessageStatus }) => {
      this.updateForm(pgMessageStatus);
    });
  }

  updateForm(pgMessageStatus: IPgMessageStatus) {
    this.editForm.patchValue({
      id: pgMessageStatus.id,
      label: pgMessageStatus.label,
      active: pgMessageStatus.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgMessageStatus = this.createFromForm();
    if (pgMessageStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.pgMessageStatusService.update(pgMessageStatus));
    } else {
      this.subscribeToSaveResponse(this.pgMessageStatusService.create(pgMessageStatus));
    }
  }

  private createFromForm(): IPgMessageStatus {
    return {
      ...new PgMessageStatus(),
      id: this.editForm.get(['id']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgMessageStatus>>) {
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
