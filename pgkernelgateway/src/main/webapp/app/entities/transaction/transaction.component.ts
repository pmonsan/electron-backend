import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITransaction } from 'app/shared/model/transaction.model';
import { AccountService } from 'app/core';
import { TransactionService } from './transaction.service';

@Component({
  selector: 'jhi-transaction',
  templateUrl: './transaction.component.html'
})
export class TransactionComponent implements OnInit, OnDestroy {
  transactions: ITransaction[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected transactionService: TransactionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.transactionService
      .query()
      .pipe(
        filter((res: HttpResponse<ITransaction[]>) => res.ok),
        map((res: HttpResponse<ITransaction[]>) => res.body)
      )
      .subscribe(
        (res: ITransaction[]) => {
          this.transactions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransaction) {
    return item.id;
  }

  registerChangeInTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('transactionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
