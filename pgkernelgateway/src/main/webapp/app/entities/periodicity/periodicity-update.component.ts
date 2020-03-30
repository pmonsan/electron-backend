import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IPeriodicity, Periodicity } from 'app/shared/model/periodicity.model';
import { PeriodicityService } from './periodicity.service';

@Component({
  selector: 'jhi-periodicity-update',
  templateUrl: './periodicity-update.component.html'
})
export class PeriodicityUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    active: [null, [Validators.required]]
  });

  constructor(protected periodicityService: PeriodicityService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ periodicity }) => {
      this.updateForm(periodicity);
    });
  }

  updateForm(periodicity: IPeriodicity) {
    this.editForm.patchValue({
      id: periodicity.id,
      label: periodicity.label,
      active: periodicity.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const periodicity = this.createFromForm();
    if (periodicity.id !== undefined) {
      this.subscribeToSaveResponse(this.periodicityService.update(periodicity));
    } else {
      this.subscribeToSaveResponse(this.periodicityService.create(periodicity));
    }
  }

  private createFromForm(): IPeriodicity {
    return {
      ...new Periodicity(),
      id: this.editForm.get(['id']).value,
      label: this.editForm.get(['label']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPeriodicity>>) {
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
