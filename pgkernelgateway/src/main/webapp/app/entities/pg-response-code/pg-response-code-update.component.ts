import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPgResponseCode, PgResponseCode } from 'app/shared/model/pg-response-code.model';
import { PgResponseCodeService } from './pg-response-code.service';

@Component({
  selector: 'jhi-pg-response-code-update',
  templateUrl: './pg-response-code-update.component.html'
})
export class PgResponseCodeUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(50)]],
    description: [],
    active: [null, [Validators.required]]
  });

  constructor(protected pgResponseCodeService: PgResponseCodeService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgResponseCode }) => {
      this.updateForm(pgResponseCode);
    });
  }

  updateForm(pgResponseCode: IPgResponseCode) {
    this.editForm.patchValue({
      id: pgResponseCode.id,
      code: pgResponseCode.code,
      label: pgResponseCode.label,
      description: pgResponseCode.description,
      active: pgResponseCode.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgResponseCode = this.createFromForm();
    if (pgResponseCode.id !== undefined) {
      this.subscribeToSaveResponse(this.pgResponseCodeService.update(pgResponseCode));
    } else {
      this.subscribeToSaveResponse(this.pgResponseCodeService.create(pgResponseCode));
    }
  }

  private createFromForm(): IPgResponseCode {
    return {
      ...new PgResponseCode(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      description: this.editForm.get(['description']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgResponseCode>>) {
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
