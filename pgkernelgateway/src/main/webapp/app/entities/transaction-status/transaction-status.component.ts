import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITransactionStatus } from 'app/shared/model/transaction-status.model';
import { AccountService } from 'app/core';
import { TransactionStatusService } from './transaction-status.service';

@Component({
  selector: 'jhi-transaction-status',
  templateUrl: './transaction-status.component.html'
})
export class TransactionStatusComponent implements OnInit, OnDestroy {
  transactionStatuses: ITransactionStatus[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected transactionStatusService: TransactionStatusService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.transactionStatusService
      .query()
      .pipe(
        filter((res: HttpResponse<ITransactionStatus[]>) => res.ok),
        map((res: HttpResponse<ITransactionStatus[]>) => res.body)
      )
      .subscribe(
        (res: ITransactionStatus[]) => {
          this.transactionStatuses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTransactionStatuses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransactionStatus) {
    return item.id;
  }

  registerChangeInTransactionStatuses() {
    this.eventSubscriber = this.eventManager.subscribe('transactionStatusListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
