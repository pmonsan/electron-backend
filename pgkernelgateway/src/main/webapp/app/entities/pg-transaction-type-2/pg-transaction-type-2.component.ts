import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgTransactionType2 } from 'app/shared/model/pg-transaction-type-2.model';
import { AccountService } from 'app/core';
import { PgTransactionType2Service } from './pg-transaction-type-2.service';

@Component({
  selector: 'jhi-pg-transaction-type-2',
  templateUrl: './pg-transaction-type-2.component.html'
})
export class PgTransactionType2Component implements OnInit, OnDestroy {
  pgTransactionType2S: IPgTransactionType2[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgTransactionType2Service: PgTransactionType2Service,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgTransactionType2Service
      .query()
      .pipe(
        filter((res: HttpResponse<IPgTransactionType2[]>) => res.ok),
        map((res: HttpResponse<IPgTransactionType2[]>) => res.body)
      )
      .subscribe(
        (res: IPgTransactionType2[]) => {
          this.pgTransactionType2S = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgTransactionType2S();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgTransactionType2) {
    return item.id;
  }

  registerChangeInPgTransactionType2S() {
    this.eventSubscriber = this.eventManager.subscribe('pgTransactionType2ListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
