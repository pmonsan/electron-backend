import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITransactionPrice } from 'app/shared/model/transaction-price.model';
import { AccountService } from 'app/core';
import { TransactionPriceService } from './transaction-price.service';

@Component({
  selector: 'jhi-transaction-price',
  templateUrl: './transaction-price.component.html'
})
export class TransactionPriceComponent implements OnInit, OnDestroy {
  transactionPrices: ITransactionPrice[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected transactionPriceService: TransactionPriceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.transactionPriceService
      .query()
      .pipe(
        filter((res: HttpResponse<ITransactionPrice[]>) => res.ok),
        map((res: HttpResponse<ITransactionPrice[]>) => res.body)
      )
      .subscribe(
        (res: ITransactionPrice[]) => {
          this.transactionPrices = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTransactionPrices();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransactionPrice) {
    return item.id;
  }

  registerChangeInTransactionPrices() {
    this.eventSubscriber = this.eventManager.subscribe('transactionPriceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
