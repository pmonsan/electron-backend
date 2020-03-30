import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgAccount } from 'app/shared/model/pg-account.model';
import { AccountService } from 'app/core';
import { PgAccountService } from './pg-account.service';

@Component({
  selector: 'jhi-pg-account',
  templateUrl: './pg-account.component.html'
})
export class PgAccountComponent implements OnInit, OnDestroy {
  pgAccounts: IPgAccount[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgAccountService: PgAccountService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgAccountService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgAccount[]>) => res.ok),
        map((res: HttpResponse<IPgAccount[]>) => res.body)
      )
      .subscribe(
        (res: IPgAccount[]) => {
          this.pgAccounts = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgAccounts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgAccount) {
    return item.id;
  }

  registerChangeInPgAccounts() {
    this.eventSubscriber = this.eventManager.subscribe('pgAccountListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
