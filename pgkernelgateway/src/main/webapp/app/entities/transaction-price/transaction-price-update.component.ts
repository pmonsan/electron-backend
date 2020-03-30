import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { ITransactionPrice, TransactionPrice } from 'app/shared/model/transaction-price.model';
import { TransactionPriceService } from './transaction-price.service';
import { ITransaction } from 'app/shared/model/transaction.model';
import { TransactionService } from 'app/entities/transaction';

@Component({
  selector: 'jhi-transaction-price-update',
  templateUrl: './transaction-price-update.component.html'
})
export class TransactionPriceUpdateComponent implements OnInit {
  isSaving: boolean;

  transactions: ITransaction[];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.maxLength(10)]],
    label: [null, [Validators.required, Validators.maxLength(100)]],
    amount: [null, [Validators.min(0)]],
    percent: [null, [Validators.min(0)]],
    amountInPercent: [null, [Validators.required]],
    amountTransactionMax: [null, [Validators.min(0)]],
    amountTransactionMin: [null, [Validators.min(0)]],
    priceCode: [null, [Validators.required, Validators.maxLength(5)]],
    serviceCode: [null, [Validators.required, Validators.maxLength(5)]],
    description: [null, [Validators.maxLength(255)]],
    modificationDate: [],
    transactionId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected transactionPriceService: TransactionPriceService,
    protected transactionService: TransactionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ transactionPrice }) => {
      this.updateForm(transactionPrice);
    });
    this.transactionService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ITransaction[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransaction[]>) => response.body)
      )
      .subscribe((res: ITransaction[]) => (this.transactions = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(transactionPrice: ITransactionPrice) {
    this.editForm.patchValue({
      id: transactionPrice.id,
      code: transactionPrice.code,
      label: transactionPrice.label,
      amount: transactionPrice.amount,
      percent: transactionPrice.percent,
      amountInPercent: transactionPrice.amountInPercent,
      amountTransactionMax: transactionPrice.amountTransactionMax,
      amountTransactionMin: transactionPrice.amountTransactionMin,
      priceCode: transactionPrice.priceCode,
      serviceCode: transactionPrice.serviceCode,
      description: transactionPrice.description,
      modificationDate: transactionPrice.modificationDate != null ? transactionPrice.modificationDate.format(DATE_TIME_FORMAT) : null,
      transactionId: transactionPrice.transactionId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const transactionPrice = this.createFromForm();
    if (transactionPrice.id !== undefined) {
      this.subscribeToSaveResponse(this.transactionPriceService.update(transactionPrice));
    } else {
      this.subscribeToSaveResponse(this.transactionPriceService.create(transactionPrice));
    }
  }

  private createFromForm(): ITransactionPrice {
    return {
      ...new TransactionPrice(),
      id: this.editForm.get(['id']).value,
      code: this.editForm.get(['code']).value,
      label: this.editForm.get(['label']).value,
      amount: this.editForm.get(['amount']).value,
      percent: this.editForm.get(['percent']).value,
      amountInPercent: this.editForm.get(['amountInPercent']).value,
      amountTransactionMax: this.editForm.get(['amountTransactionMax']).value,
      amountTransactionMin: this.editForm.get(['amountTransactionMin']).value,
      priceCode: this.editForm.get(['priceCode']).value,
      serviceCode: this.editForm.get(['serviceCode']).value,
      description: this.editForm.get(['description']).value,
      modificationDate:
        this.editForm.get(['modificationDate']).value != null
          ? moment(this.editForm.get(['modificationDate']).value, DATE_TIME_FORMAT)
          : undefined,
      transactionId: this.editForm.get(['transactionId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITransactionPrice>>) {
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

  trackTransactionById(index: number, item: ITransaction) {
    return item.id;
  }
}
