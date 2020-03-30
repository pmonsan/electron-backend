import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITransactionGroup } from 'app/shared/model/transaction-group.model';
import { AccountService } from 'app/core';
import { TransactionGroupService } from './transaction-group.service';

@Component({
  selector: 'jhi-transaction-group',
  templateUrl: './transaction-group.component.html'
})
export class TransactionGroupComponent implements OnInit, OnDestroy {
  transactionGroups: ITransactionGroup[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected transactionGroupService: TransactionGroupService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.transactionGroupService
      .query()
      .pipe(
        filter((res: HttpResponse<ITransactionGroup[]>) => res.ok),
        map((res: HttpResponse<ITransactionGroup[]>) => res.body)
      )
      .subscribe(
        (res: ITransactionGroup[]) => {
          this.transactionGroups = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTransactionGroups();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransactionGroup) {
    return item.id;
  }

  registerChangeInTransactionGroups() {
    this.eventSubscriber = this.eventManager.subscribe('transactionGroupListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
