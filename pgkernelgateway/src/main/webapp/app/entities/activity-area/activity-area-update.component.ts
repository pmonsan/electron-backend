import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IActivityArea, ActivityArea } from 'app/shared/model/activity-area.model';
import { ActivityAreaService } from './activity-area.service';

@Component({
  selector: 'jhi-activity-area-update',
  templateUrl: './activity-area-update.component.html'
})
export class ActivityAreaUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(protected activityAreaService: ActivityAreaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ activityArea }) => {
      this.updateForm(activityArea);
    });
  }

  updateForm(activityArea: IActivityArea) {
    this.editForm.patchValue({
      id: activityArea.id,
      code: activityArea.code,
      label: activityArea.label,
      active: activityArea.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const activityArea = this.createFromForm();
    if (activityArea.id !== undefined) {
      this.subscribeToSaveResponse(this.activityAreaService.update(activityArea));
    } else {
      this.subscribeToSaveResponse(this.activityAreaService.create(activityArea));
    }
  }

  private createFromForm(): IActivityArea {
    return {
      ...new ActivityArea(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivityArea>>) {
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
