import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITransactionCommission } from 'app/shared/model/transaction-commission.model';
import { AccountService } from 'app/core';
import { TransactionCommissionService } from './transaction-commission.service';

@Component({
  selector: 'jhi-transaction-commission',
  templateUrl: './transaction-commission.component.html'
})
export class TransactionCommissionComponent implements OnInit, OnDestroy {
  transactionCommissions: ITransactionCommission[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected transactionCommissionService: TransactionCommissionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.transactionCommissionService
      .query()
      .pipe(
        filter((res: HttpResponse<ITransactionCommission[]>) => res.ok),
        map((res: HttpResponse<ITransactionCommission[]>) => res.body)
      )
      .subscribe(
        (res: ITransactionCommission[]) => {
          this.transactionCommissions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTransactionCommissions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransactionCommission) {
    return item.id;
  }

  registerChangeInTransactionCommissions() {
    this.eventSubscriber = this.eventManager.subscribe('transactionCommissionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
