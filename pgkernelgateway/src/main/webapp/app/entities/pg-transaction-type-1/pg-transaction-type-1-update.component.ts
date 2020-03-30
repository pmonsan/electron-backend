import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPgTransactionType1, PgTransactionType1 } from 'app/shared/model/pg-transaction-type-1.model';
import { PgTransactionType1Service } from './pg-transaction-type-1.service';

@Component({
  selector: 'jhi-pg-transaction-type-1-update',
  templateUrl: './pg-transaction-type-1-update.component.html'
})
export class PgTransactionType1UpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [],
    description: [],
    active: [null, [Validators.required]]
  });

  constructor(
    protected pgTransactionType1Service: PgTransactionType1Service,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgTransactionType1 }) => {
      this.updateForm(pgTransactionType1);
    });
  }

  updateForm(pgTransactionType1: IPgTransactionType1) {
    this.editForm.patchValue({
      id: pgTransactionType1.id,
      code: pgTransactionType1.code,
      label: pgTransactionType1.label,
      description: pgTransactionType1.description,
      active: pgTransactionType1.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgTransactionType1 = this.createFromForm();
    if (pgTransactionType1.id !== undefined) {
      this.subscribeToSaveResponse(this.pgTransactionType1Service.update(pgTransactionType1));
    } else {
      this.subscribeToSaveResponse(this.pgTransactionType1Service.create(pgTransactionType1));
    }
  }

  private createFromForm(): IPgTransactionType1 {
    return {
      ...new PgTransactionType1(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      description: this.editForm.get(['description']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgTransactionType1>>) {
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
