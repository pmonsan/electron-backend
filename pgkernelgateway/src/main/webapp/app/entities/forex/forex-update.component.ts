import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IForex, Forex } from 'app/shared/model/forex.model';
import { ForexService } from './forex.service';
import { ICurrency } from 'app/shared/model/currency.model';
import { CurrencyService } from 'app/entities/currency';

@Component({
  selector: 'jhi-forex-update',
  templateUrl: './forex-update.component.html'
})
export class ForexUpdateComponent implements OnInit {
  isSaving: boolean;

  currencies: ICurrency[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(5)]],
    rate: [],
    creationDate: [],
    active: [null, [Validators.required]],
    fromCurrencyId: [],
    toCurrencyId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected forexService: ForexService,
    protected currencyService: CurrencyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ forex }) => {
      this.updateForm(forex);
    });
    this.currencyService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICurrency[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICurrency[]>) => response.body)
      )
      .subscribe((res: ICurrency[]) => (this.currencies = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(forex: IForex) {
    this.editForm.patchValue({
      id: forex.id,
      code: forex.code,
      rate: forex.rate,
      creationDate: forex.creationDate != null ? forex.creationDate.format(DATE_TIME_FORMAT) : null,
      active: forex.active,
      fromCurrencyId: forex.fromCurrencyId,
      toCurrencyId: forex.toCurrencyId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const forex = this.createFromForm();
    if (forex.id !== undefined) {
      this.subscribeToSaveResponse(this.forexService.update(forex));
    } else {
      this.subscribeToSaveResponse(this.forexService.create(forex));
    }
  }

  private createFromForm(): IForex {
    return {
      ...new Forex(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      rate: this.editForm.get(['rate']).value,
      creationDate:
        this.editForm.get(['creationDate']).value != null ? moment(this.editForm.get(['creationDate']).value, DATE_TIME_FORMAT) : undefined,
      active: this.editForm.get(['active']).value,
      fromCurrencyId: this.editForm.get(['fromCurrencyId']).value,
      toCurrencyId: this.editForm.get(['toCurrencyId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IForex>>) {
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

  trackCurrencyById(index: number, item: ICurrency) {
    return item.id;
  }
}
