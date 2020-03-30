import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IPrice, Price } from 'app/shared/model/price.model';
import { PriceService } from './price.service';
import { IPricePlan } from 'app/shared/model/price-plan.model';
import { PricePlanService } from 'app/entities/price-plan';

@Component({
  selector: 'jhi-price-update',
  templateUrl: './price-update.component.html'
})
export class PriceUpdateComponent implements OnInit {
  isSaving: boolean;

  priceplans: IPricePlan[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    amount: [null, [Validators.min(0)]],
    percent: [null, [Validators.min(0)]],
    amountInPercent: [null, [Validators.required]],
    amountTransactionMax: [null, [Validators.min(0)]],
    amountTransactionMin: [null, [Validators.min(0)]],
    currencyCode: [null, [Validators.required, Validators.maxLength(5)]],
    serviceCode: [null, [Validators.required, Validators.maxLength(5)]],
    description: [null, [Validators.maxLength(255)]],
    modificationDate: [],
    active: [null, [Validators.required]],
    pricePlanId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected priceService: PriceService,
    protected pricePlanService: PricePlanService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ price }) => {
      this.updateForm(price);
    });
    this.pricePlanService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPricePlan[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPricePlan[]>) => response.body)
      )
      .subscribe((res: IPricePlan[]) => (this.priceplans = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(price: IPrice) {
    this.editForm.patchValue({
      id: price.id,
      code: price.code,
      label: price.label,
      amount: price.amount,
      percent: price.percent,
      amountInPercent: price.amountInPercent,
      amountTransactionMax: price.amountTransactionMax,
      amountTransactionMin: price.amountTransactionMin,
      currencyCode: price.currencyCode,
      serviceCode: price.serviceCode,
      description: price.description,
      modificationDate: price.modificationDate != null ? price.modificationDate.format(DATE_TIME_FORMAT) : null,
      active: price.active,
      pricePlanId: price.pricePlanId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const price = this.createFromForm();
    if (price.id !== undefined) {
      this.subscribeToSaveResponse(this.priceService.update(price));
    } else {
      this.subscribeToSaveResponse(this.priceService.create(price));
    }
  }

  private createFromForm(): IPrice {
    return {
      ...new Price(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      amount: this.editForm.get(['amount']).value,
      percent: this.editForm.get(['percent']).value,
      amountInPercent: this.editForm.get(['amountInPercent']).value,
      amountTransactionMax: this.editForm.get(['amountTransactionMax']).value,
      amountTransactionMin: this.editForm.get(['amountTransactionMin']).value,
      currencyCode: this.editForm.get(['currencyCode']).value,
      serviceCode: this.editForm.get(['serviceCode']).value,
      description: this.editForm.get(['description']).value,
      modificationDate:
        this.editForm.get(['modificationDate']).value != null
          ? moment(this.editForm.get(['modificationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      active: this.editForm.get(['active']).value,
      pricePlanId: this.editForm.get(['pricePlanId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrice>>) {
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
