import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICustomerBlacklist } from 'app/shared/model/customer-blacklist.model';
import { AccountService } from 'app/core';
import { CustomerBlacklistService } from './customer-blacklist.service';

@Component({
  selector: 'jhi-customer-blacklist',
  templateUrl: './customer-blacklist.component.html'
})
export class CustomerBlacklistComponent implements OnInit, OnDestroy {
  customerBlacklists: ICustomerBlacklist[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected customerBlacklistService: CustomerBlacklistService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.customerBlacklistService
      .query()
      .pipe(
        filter((res: HttpResponse<ICustomerBlacklist[]>) => res.ok),
        map((res: HttpResponse<ICustomerBlacklist[]>) => res.body)
      )
      .subscribe(
        (res: ICustomerBlacklist[]) => {
          this.customerBlacklists = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCustomerBlacklists();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICustomerBlacklist) {
    return item.id;
  }

  registerChangeInCustomerBlacklists() {
    this.eventSubscriber = this.eventManager.subscribe('customerBlacklistListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
