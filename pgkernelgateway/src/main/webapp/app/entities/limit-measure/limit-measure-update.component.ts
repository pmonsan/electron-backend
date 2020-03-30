import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ILimitMeasure, LimitMeasure } from 'app/shared/model/limit-measure.model';
import { LimitMeasureService } from './limit-measure.service';

@Component({
  selector: 'jhi-limit-measure-update',
  templateUrl: './limit-measure-update.component.html'
})
export class LimitMeasureUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(protected limitMeasureService: LimitMeasureService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ limitMeasure }) => {
      this.updateForm(limitMeasure);
    });
  }

  updateForm(limitMeasure: ILimitMeasure) {
    this.editForm.patchValue({
      id: limitMeasure.id,
      label: limitMeasure.label,
      active: limitMeasure.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const limitMeasure = this.createFromForm();
    if (limitMeasure.id !== undefined) {
      this.subscribeToSaveResponse(this.limitMeasureService.update(limitMeasure));
    } else {
      this.subscribeToSaveResponse(this.limitMeasureService.create(limitMeasure));
    }
  }

  private createFromForm(): ILimitMeasure {
    return {
      ...new LimitMeasure(),
      id: this.editForm.get(['id']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILimitMeasure>>) {
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
