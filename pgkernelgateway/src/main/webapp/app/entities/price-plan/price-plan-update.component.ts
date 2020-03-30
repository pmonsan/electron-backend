import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPricePlan, PricePlan } from 'app/shared/model/price-plan.model';
import { PricePlanService } from './price-plan.service';

@Component({
  selector: 'jhi-price-plan-update',
  templateUrl: './price-plan-update.component.html'
})
export class PricePlanUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    startDate: [null, [Validators.required]],
    endDate: [null, [Validators.required]],
    active: [null, [Validators.required]]
  });

  constructor(protected pricePlanService: PricePlanService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ pricePlan }) => {
      this.updateForm(pricePlan);
    });
  }

  updateForm(pricePlan: IPricePlan) {
    this.editForm.patchValue({
      id: pricePlan.id,
      label: pricePlan.label,
      startDate: pricePlan.startDate != null ? pricePlan.startDate.format(DATE_TIME_FORMAT) : null,
      endDate: pricePlan.endDate != null ? pricePlan.endDate.format(DATE_TIME_FORMAT) : null,
      active: pricePlan.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const pricePlan = this.createFromForm();
    if (pricePlan.id !== undefined) {
      this.subscribeToSaveResponse(this.pricePlanService.update(pricePlan));
    } else {
      this.subscribeToSaveResponse(this.pricePlanService.create(pricePlan));
    }
  }

  private createFromForm(): IPricePlan {
    return {
      ...new PricePlan(),
      id: this.editForm.get(['id']).value,
      label: this.editForm.get(['label']).value,
      startDate:
        this.editForm.get(['startDate']).value != null ? moment(this.editForm.get(['startDate']).value, DATE_TIME_FORMAT) : undefined,
      endDate: this.editForm.get(['endDate']).value != null ? moment(this.editForm.get(['endDate']).value, DATE_TIME_FORMAT) : undefined,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPricePlan>>) {
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
