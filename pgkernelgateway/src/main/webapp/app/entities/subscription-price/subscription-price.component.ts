import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISubscriptionPrice } from 'app/shared/model/subscription-price.model';
import { AccountService } from 'app/core';
import { SubscriptionPriceService } from './subscription-price.service';

@Component({
  selector: 'jhi-subscription-price',
  templateUrl: './subscription-price.component.html'
})
export class SubscriptionPriceComponent implements OnInit, OnDestroy {
  subscriptionPrices: ISubscriptionPrice[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected subscriptionPriceService: SubscriptionPriceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.subscriptionPriceService
      .query()
      .pipe(
        filter((res: HttpResponse<ISubscriptionPrice[]>) => res.ok),
        map((res: HttpResponse<ISubscriptionPrice[]>) => res.body)
      )
      .subscribe(
        (res: ISubscriptionPrice[]) => {
          this.subscriptionPrices = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInSubscriptionPrices();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ISubscriptionPrice) {
    return item.id;
  }

  registerChangeInSubscriptionPrices() {
    this.eventSubscriber = this.eventManager.subscribe('subscriptionPriceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
