import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPgApplication, PgApplication } from 'app/shared/model/pg-application.model';
import { PgApplicationService } from './pg-application.service';

@Component({
  selector: 'jhi-pg-application-update',
  templateUrl: './pg-application-update.component.html'
})
export class PgApplicationUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(50)]],
    partnerCode: [null, [Validators.maxLength(5)]],
    active: [null, [Validators.required]]
  });

  constructor(protected pgApplicationService: PgApplicationService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgApplication }) => {
      this.updateForm(pgApplication);
    });
  }

  updateForm(pgApplication: IPgApplication) {
    this.editForm.patchValue({
      id: pgApplication.id,
      code: pgApplication.code,
      label: pgApplication.label,
      partnerCode: pgApplication.partnerCode,
      active: pgApplication.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgApplication = this.createFromForm();
    if (pgApplication.id !== undefined) {
      this.subscribeToSaveResponse(this.pgApplicationService.update(pgApplication));
    } else {
      this.subscribeToSaveResponse(this.pgApplicationService.create(pgApplication));
    }
  }

  private createFromForm(): IPgApplication {
    return {
      ...new PgApplication(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      partnerCode: this.editForm.get(['partnerCode']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgApplication>>) {
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
