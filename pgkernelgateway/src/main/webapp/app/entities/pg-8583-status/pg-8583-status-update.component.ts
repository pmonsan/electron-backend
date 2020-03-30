import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPg8583Status, Pg8583Status } from 'app/shared/model/pg-8583-status.model';
import { Pg8583StatusService } from './pg-8583-status.service';

@Component({
  selector: 'jhi-pg-8583-status-update',
  templateUrl: './pg-8583-status-update.component.html'
})
export class Pg8583StatusUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    status: [null, [Validators.required, Validators.maxLength(100)]],
    defaultReason: [null, [Validators.maxLength(100)]]
  });

  constructor(protected pg8583StatusService: Pg8583StatusService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pg8583Status }) => {
      this.updateForm(pg8583Status);
    });
  }

  updateForm(pg8583Status: IPg8583Status) {
    this.editForm.patchValue({
      id: pg8583Status.id,
      status: pg8583Status.status,
      defaultReason: pg8583Status.defaultReason
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pg8583Status = this.createFromForm();
    if (pg8583Status.id !== undefined) {
      this.subscribeToSaveResponse(this.pg8583StatusService.update(pg8583Status));
    } else {
      this.subscribeToSaveResponse(this.pg8583StatusService.create(pg8583Status));
    }
  }

  private createFromForm(): IPg8583Status {
    return {
      ...new Pg8583Status(),
      id: this.editForm.get(['id']).value,
      status: this.editForm.get(['status']).value,
      defaultReason: this.editForm.get(['defaultReason']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPg8583Status>>) {
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
