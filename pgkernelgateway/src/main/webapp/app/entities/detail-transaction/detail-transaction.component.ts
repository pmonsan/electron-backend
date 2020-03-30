import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDetailTransaction } from 'app/shared/model/detail-transaction.model';
import { AccountService } from 'app/core';
import { DetailTransactionService } from './detail-transaction.service';

@Component({
  selector: 'jhi-detail-transaction',
  templateUrl: './detail-transaction.component.html'
})
export class DetailTransactionComponent implements OnInit, OnDestroy {
  detailTransactions: IDetailTransaction[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected detailTransactionService: DetailTransactionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.detailTransactionService
      .query()
      .pipe(
        filter((res: HttpResponse<IDetailTransaction[]>) => res.ok),
        map((res: HttpResponse<IDetailTransaction[]>) => res.body)
      )
      .subscribe(
        (res: IDetailTransaction[]) => {
          this.detailTransactions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDetailTransactions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDetailTransaction) {
    return item.id;
  }

  registerChangeInDetailTransactions() {
    this.eventSubscriber = this.eventManager.subscribe('detailTransactionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
