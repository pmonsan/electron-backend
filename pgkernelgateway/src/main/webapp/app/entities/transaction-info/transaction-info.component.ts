import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITransactionInfo } from 'app/shared/model/transaction-info.model';
import { AccountService } from 'app/core';
import { TransactionInfoService } from './transaction-info.service';

@Component({
  selector: 'jhi-transaction-info',
  templateUrl: './transaction-info.component.html'
})
export class TransactionInfoComponent implements OnInit, OnDestroy {
  transactionInfos: ITransactionInfo[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected transactionInfoService: TransactionInfoService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.transactionInfoService
      .query()
      .pipe(
        filter((res: HttpResponse<ITransactionInfo[]>) => res.ok),
        map((res: HttpResponse<ITransactionInfo[]>) => res.body)
      )
      .subscribe(
        (res: ITransactionInfo[]) => {
          this.transactionInfos = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTransactionInfos();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransactionInfo) {
    return item.id;
  }

  registerChangeInTransactionInfos() {
    this.eventSubscriber = this.eventManager.subscribe('transactionInfoListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
