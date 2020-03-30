import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICustomerLimit } from 'app/shared/model/customer-limit.model';
import { AccountService } from 'app/core';
import { CustomerLimitService } from './customer-limit.service';

@Component({
  selector: 'jhi-customer-limit',
  templateUrl: './customer-limit.component.html'
})
export class CustomerLimitComponent implements OnInit, OnDestroy {
  customerLimits: ICustomerLimit[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected customerLimitService: CustomerLimitService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.customerLimitService
      .query()
      .pipe(
        filter((res: HttpResponse<ICustomerLimit[]>) => res.ok),
        map((res: HttpResponse<ICustomerLimit[]>) => res.body)
      )
      .subscribe(
        (res: ICustomerLimit[]) => {
          this.customerLimits = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCustomerLimits();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICustomerLimit) {
    return item.id;
  }

  registerChangeInCustomerLimits() {
    this.eventSubscriber = this.eventManager.subscribe('customerLimitListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
