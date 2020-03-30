import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAccountBalance } from 'app/shared/model/account-balance.model';
import { AccountService } from 'app/core';
import { AccountBalanceService } from './account-balance.service';

@Component({
  selector: 'jhi-account-balance',
  templateUrl: './account-balance.component.html'
})
export class AccountBalanceComponent implements OnInit, OnDestroy {
  accountBalances: IAccountBalance[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected accountBalanceService: AccountBalanceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.accountBalanceService
      .query()
      .pipe(
        filter((res: HttpResponse<IAccountBalance[]>) => res.ok),
        map((res: HttpResponse<IAccountBalance[]>) => res.body)
      )
      .subscribe(
        (res: IAccountBalance[]) => {
          this.accountBalances = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAccountBalances();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAccountBalance) {
    return item.id;
  }

  registerChangeInAccountBalances() {
    this.eventSubscriber = this.eventManager.subscribe('accountBalanceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
