import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICustomerSubscription } from 'app/shared/model/customer-subscription.model';
import { AccountService } from 'app/core';
import { CustomerSubscriptionService } from './customer-subscription.service';

@Component({
  selector: 'jhi-customer-subscription',
  templateUrl: './customer-subscription.component.html'
})
export class CustomerSubscriptionComponent implements OnInit, OnDestroy {
  customerSubscriptions: ICustomerSubscription[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected customerSubscriptionService: CustomerSubscriptionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.customerSubscriptionService
      .query()
      .pipe(
        filter((res: HttpResponse<ICustomerSubscription[]>) => res.ok),
        map((res: HttpResponse<ICustomerSubscription[]>) => res.body)
      )
      .subscribe(
        (res: ICustomerSubscription[]) => {
          this.customerSubscriptions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCustomerSubscriptions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICustomerSubscription) {
    return item.id;
  }

  registerChangeInCustomerSubscriptions() {
    this.eventSubscriber = this.eventManager.subscribe('customerSubscriptionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
