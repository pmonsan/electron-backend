import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPgBatch, PgBatch } from 'app/shared/model/pg-batch.model';
import { PgBatchService } from './pg-batch.service';

@Component({
  selector: 'jhi-pg-batch-update',
  templateUrl: './pg-batch-update.component.html'
})
export class PgBatchUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.maxLength(100)]],
    expectedEndDate: [null, [Validators.required]],
    batchDate: [null, [Validators.required]],
    active: [null, [Validators.required]]
  });

  constructor(protected pgBatchService: PgBatchService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgBatch }) => {
      this.updateForm(pgBatch);
    });
  }

  updateForm(pgBatch: IPgBatch) {
    this.editForm.patchValue({
      id: pgBatch.id,
      code: pgBatch.code,
      label: pgBatch.label,
      expectedEndDate: pgBatch.expectedEndDate != null ? pgBatch.expectedEndDate.format(DATE_TIME_FORMAT) : null,
      batchDate: pgBatch.batchDate != null ? pgBatch.batchDate.format(DATE_TIME_FORMAT) : null,
      active: pgBatch.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgBatch = this.createFromForm();
    if (pgBatch.id !== undefined) {
      this.subscribeToSaveResponse(this.pgBatchService.update(pgBatch));
    } else {
      this.subscribeToSaveResponse(this.pgBatchService.create(pgBatch));
    }
  }

  private createFromForm(): IPgBatch {
    return {
      ...new PgBatch(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      expectedEndDate:
        this.editForm.get(['expectedEndDate']).value != null
          ? moment(this.editForm.get(['expectedEndDate']).value, DATE_TIME_FORMAT)
          : undefined,
      batchDate:
        this.editForm.get(['batchDate']).value != null ? moment(this.editForm.get(['batchDate']).value, DATE_TIME_FORMAT) : undefined,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgBatch>>) {
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
