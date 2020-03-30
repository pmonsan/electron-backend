import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IPriceCommission, PriceCommission } from 'app/shared/model/price-commission.model';
import { PriceCommissionService } from './price-commission.service';

@Component({
  selector: 'jhi-price-commission-update',
  templateUrl: './price-commission-update.component.html'
})
export class PriceCommissionUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(10)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    amount: [null, [Validators.min(0)]],
    amountInPercent: [null, [Validators.required]],
    dateCreation: [null, [Validators.required]],
    percent: [null, [Validators.min(0)]],
    currencyCode: [null, [Validators.required, Validators.maxLength(10)]],
    partnerCode: [null, [Validators.required, Validators.maxLength(10)]],
    serviceCode: [null, [Validators.required, Validators.maxLength(10)]],
    description: [null, [Validators.maxLength(255)]],
    active: [null, [Validators.required]]
  });

  constructor(
    protected priceCommissionService: PriceCommissionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ priceCommission }) => {
      this.updateForm(priceCommission);
    });
  }

  updateForm(priceCommission: IPriceCommission) {
    this.editForm.patchValue({
      id: priceCommission.id,
      code: priceCommission.code,
      label: priceCommission.label,
      amount: priceCommission.amount,
      amountInPercent: priceCommission.amountInPercent,
      dateCreation: priceCommission.dateCreation != null ? priceCommission.dateCreation.format(DATE_TIME_FORMAT) : null,
      percent: priceCommission.percent,
      currencyCode: priceCommission.currencyCode,
      partnerCode: priceCommission.partnerCode,
      serviceCode: priceCommission.serviceCode,
      description: priceCommission.description,
      active: priceCommission.active
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const priceCommission = this.createFromForm();
    if (priceCommission.id !== undefined) {
      this.subscribeToSaveResponse(this.priceCommissionService.update(priceCommission));
    } else {
      this.subscribeToSaveResponse(this.priceCommissionService.create(priceCommission));
    }
  }

  private createFromForm(): IPriceCommission {
    return {
      ...new PriceCommission(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      amount: this.editForm.get(['amount']).value,
      amountInPercent: this.editForm.get(['amountInPercent']).value,
      dateCreation:
        this.editForm.get(['dateCreation']).value != null ? moment(this.editForm.get(['dateCreation']).value, DATE_TIME_FORMAT) : undefined,
      percent: this.editForm.get(['percent']).value,
      currencyCode: this.editForm.get(['currencyCode']).value,
      partnerCode: this.editForm.get(['partnerCode']).value,
      serviceCode: this.editForm.get(['serviceCode']).value,
      description: this.editForm.get(['description']).value,
      active: this.editForm.get(['active']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPriceCommission>>) {
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
