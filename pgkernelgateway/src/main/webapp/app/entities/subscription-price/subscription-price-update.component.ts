import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ISubscriptionPrice, SubscriptionPrice } from 'app/shared/model/subscription-price.model';
import { SubscriptionPriceService } from './subscription-price.service';
import { IPricePlan } from 'app/shared/model/price-plan.model';
import { PricePlanService } from 'app/entities/price-plan';

@Component({
  selector: 'jhi-subscription-price-update',
  templateUrl: './subscription-price-update.component.html'
})
export class SubscriptionPriceUpdateComponent implements OnInit {
  isSaving: boolean;

  priceplans: IPricePlan[];

  editForm = this.fb.group({
    id: [],
    amount: [null, [Validators.min(0)]],
    description: [null, [Validators.maxLength(255)]],
    label: [null, [Validators.maxLength(100)]],
    modificationDate: [],
    serviceCode: [null, [Validators.required, Validators.maxLength(10)]],
    accountTypeCode: [null, [Validators.required, Validators.maxLength(5)]],
    currencyCode: [null, [Validators.required, Validators.maxLength(5)]],
    active: [null, [Validators.required]],
    pricePlanId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected subscriptionPriceService: SubscriptionPriceService,
    protected pricePlanService: PricePlanService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ subscriptionPrice }) => {
      this.updateForm(subscriptionPrice);
    });
    this.pricePlanService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPricePlan[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPricePlan[]>) => response.body)
      )
      .subscribe((res: IPricePlan[]) => (this.priceplans = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(subscriptionPrice: ISubscriptionPrice) {
    this.editForm.patchValue({
      id: subscriptionPrice.id,
      amount: subscriptionPrice.amount,
      description: subscriptionPrice.description,
      label: subscriptionPrice.label,
      modificationDate: subscriptionPrice.modificationDate != null ? subscriptionPrice.modificationDate.format(DATE_TIME_FORMAT) : null,
      serviceCode: subscriptionPrice.serviceCode,
      accountTypeCode: subscriptionPrice.accountTypeCode,
      currencyCode: subscriptionPrice.currencyCode,
      active: subscriptionPrice.active,
      pricePlanId: subscriptionPrice.pricePlanId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const subscriptionPrice = this.createFromForm();
    if (subscriptionPrice.id !== undefined) {
      this.subscribeToSaveResponse(this.subscriptionPriceService.update(subscriptionPrice));
    } else {
      this.subscribeToSaveResponse(this.subscriptionPriceService.create(subscriptionPrice));
    }
  }

  private createFromForm(): ISubscriptionPrice {
    return {
      ...new SubscriptionPrice(),
      id: this.editForm.get(['id']).value,
      amount: this.editForm.get(['amount']).value,
      description: this.editForm.get(['description']).value,
      label: this.editForm.get(['label']).value,
      modificationDate:
        this.editForm.get(['modificationDate']).value != null
          ? moment(this.editForm.get(['modificationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      serviceCode: this.editForm.get(['serviceCode']).value,
      accountTypeCode: this.editForm.get(['accountTypeCode']).value,
      currencyCode: this.editForm.get(['currencyCode']).value,
      active: this.editForm.get(['active']).value,
      pricePlanId: this.editForm.get(['pricePlanId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISubscriptionPrice>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackPricePlanById(index: number, item: IPricePlan) {
    return item.id;
  }
}
