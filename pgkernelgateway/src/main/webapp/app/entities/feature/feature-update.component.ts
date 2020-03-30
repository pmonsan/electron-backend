import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IFeature, Feature } from 'app/shared/model/feature.model';
import { FeatureService } from './feature.service';

@Component({
  selector: 'jhi-feature-update',
  templateUrl: './feature-update.component.html'
})
export class FeatureUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    comment: [null, [Validators.maxLength(255)]],
    longLabel: [null, [Validators.required, Validators.maxLength(100)]],
    shortLabel: [null, [Validators.maxLength(50)]],
    active: [null, [Validators.required]]
  });

  constructor(protected featureService: FeatureService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ feature }) => {
      this.updateForm(feature);
    });
  }

  updateForm(feature: IFeature) {
    this.editForm.patchValue({
      id: feature.id,
      code: feature.code,
      comment: feature.comment,
      longLabel: feature.longLabel,
      shortLabel: feature.shortLabel,
      active: feature.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const feature = this.createFromForm();
    if (feature.id !== undefined) {
      this.subscribeToSaveResponse(this.featureService.update(feature));
    } else {
      this.subscribeToSaveResponse(this.featureService.create(feature));
    }
  }

  private createFromForm(): IFeature {
    return {
      ...new Feature(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      comment: this.editForm.get(['comment']).value,
      longLabel: this.editForm.get(['longLabel']).value,
      shortLabel: this.editForm.get(['shortLabel']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFeature>>) {
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
