import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPrice } from 'app/shared/model/price.model';
import { AccountService } from 'app/core';
import { PriceService } from './price.service';

@Component({
  selector: 'jhi-price',
  templateUrl: './price.component.html'
})
export class PriceComponent implements OnInit, OnDestroy {
  prices: IPrice[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected priceService: PriceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.priceService
      .query()
      .pipe(
        filter((res: HttpResponse<IPrice[]>) => res.ok),
        map((res: HttpResponse<IPrice[]>) => res.body)
      )
      .subscribe(
        (res: IPrice[]) => {
          this.prices = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPrices();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPrice) {
    return item.id;
  }

  registerChangeInPrices() {
    this.eventSubscriber = this.eventManager.subscribe('priceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
