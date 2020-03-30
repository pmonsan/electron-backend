import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPgMessageModel, PgMessageModel } from 'app/shared/model/pg-message-model.model';
import { PgMessageModelService } from './pg-message-model.service';

@Component({
  selector: 'jhi-pg-message-model-update',
  templateUrl: './pg-message-model-update.component.html'
})
export class PgMessageModelUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(50)]],
    comment: [],
    active: [null, [Validators.required]]
  });

  constructor(protected pgMessageModelService: PgMessageModelService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgMessageModel }) => {
      this.updateForm(pgMessageModel);
    });
  }

  updateForm(pgMessageModel: IPgMessageModel) {
    this.editForm.patchValue({
      id: pgMessageModel.id,
      code: pgMessageModel.code,
      label: pgMessageModel.label,
      comment: pgMessageModel.comment,
      active: pgMessageModel.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgMessageModel = this.createFromForm();
    if (pgMessageModel.id !== undefined) {
      this.subscribeToSaveResponse(this.pgMessageModelService.update(pgMessageModel));
    } else {
      this.subscribeToSaveResponse(this.pgMessageModelService.create(pgMessageModel));
    }
  }

  private createFromForm(): IPgMessageModel {
    return {
      ...new PgMessageModel(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      comment: this.editForm.get(['comment']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgMessageModel>>) {
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
