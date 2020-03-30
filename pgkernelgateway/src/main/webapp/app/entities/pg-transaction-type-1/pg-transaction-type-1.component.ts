import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgTransactionType1 } from 'app/shared/model/pg-transaction-type-1.model';
import { AccountService } from 'app/core';
import { PgTransactionType1Service } from './pg-transaction-type-1.service';

@Component({
  selector: 'jhi-pg-transaction-type-1',
  templateUrl: './pg-transaction-type-1.component.html'
})
export class PgTransactionType1Component implements OnInit, OnDestroy {
  pgTransactionType1S: IPgTransactionType1[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgTransactionType1Service: PgTransactionType1Service,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgTransactionType1Service
      .query()
      .pipe(
        filter((res: HttpResponse<IPgTransactionType1[]>) => res.ok),
        map((res: HttpResponse<IPgTransactionType1[]>) => res.body)
      )
      .subscribe(
        (res: IPgTransactionType1[]) => {
          this.pgTransactionType1S = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgTransactionType1S();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgTransactionType1) {
    return item.id;
  }

  registerChangeInPgTransactionType1S() {
    this.eventSubscriber = this.eventManager.subscribe('pgTransactionType1ListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
