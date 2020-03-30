import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPgTransactionType2, PgTransactionType2 } from 'app/shared/model/pg-transaction-type-2.model';
import { PgTransactionType2Service } from './pg-transaction-type-2.service';

@Component({
  selector: 'jhi-pg-transaction-type-2-update',
  templateUrl: './pg-transaction-type-2-update.component.html'
})
export class PgTransactionType2UpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(50)]],
    description: [null, [Validators.required, Validators.maxLength(50)]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected pgTransactionType2Service: PgTransactionType2Service,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgTransactionType2 }) => {
      this.updateForm(pgTransactionType2);
    });
  }

  updateForm(pgTransactionType2: IPgTransactionType2) {
    this.editForm.patchValue({
      id: pgTransactionType2.id,
      code: pgTransactionType2.code,
      label: pgTransactionType2.label,
      description: pgTransactionType2.description,
      active: pgTransactionType2.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgTransactionType2 = this.createFromForm();
    if (pgTransactionType2.id !== undefined) {
      this.subscribeToSaveResponse(this.pgTransactionType2Service.update(pgTransactionType2));
    } else {
      this.subscribeToSaveResponse(this.pgTransactionType2Service.create(pgTransactionType2));
    }
  }

  private createFromForm(): IPgTransactionType2 {
    return {
      ...new PgTransactionType2(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      description: this.editForm.get(['description']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgTransactionType2>>) {
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
