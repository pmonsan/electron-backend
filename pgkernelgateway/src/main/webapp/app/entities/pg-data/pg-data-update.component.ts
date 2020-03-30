import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPgData, PgData } from 'app/shared/model/pg-data.model';
import { PgDataService } from './pg-data.service';

@Component({
  selector: 'jhi-pg-data-update',
  templateUrl: './pg-data-update.component.html'
})
export class PgDataUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    longLabel: [null, [Validators.maxLength(100)]],
    shortLabel: [null, [Validators.maxLength(50)]],
    comment: [null, [Validators.maxLength(255)]],
    active: [null, [Validators.required]]
  });

  constructor(protected pgDataService: PgDataService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pgData }) => {
      this.updateForm(pgData);
    });
  }

  updateForm(pgData: IPgData) {
    this.editForm.patchValue({
      id: pgData.id,
      code: pgData.code,
      longLabel: pgData.longLabel,
      shortLabel: pgData.shortLabel,
      comment: pgData.comment,
      active: pgData.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pgData = this.createFromForm();
    if (pgData.id !== undefined) {
      this.subscribeToSaveResponse(this.pgDataService.update(pgData));
    } else {
      this.subscribeToSaveResponse(this.pgDataService.create(pgData));
    }
  }

  private createFromForm(): IPgData {
    return {
      ...new PgData(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      longLabel: this.editForm.get(['longLabel']).value,
      shortLabel: this.editForm.get(['shortLabel']).value,
      comment: this.editForm.get(['comment']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPgData>>) {
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
