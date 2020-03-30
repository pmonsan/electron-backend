import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITransactionProperty } from 'app/shared/model/transaction-property.model';
import { AccountService } from 'app/core';
import { TransactionPropertyService } from './transaction-property.service';

@Component({
  selector: 'jhi-transaction-property',
  templateUrl: './transaction-property.component.html'
})
export class TransactionPropertyComponent implements OnInit, OnDestroy {
  transactionProperties: ITransactionProperty[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected transactionPropertyService: TransactionPropertyService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.transactionPropertyService
      .query()
      .pipe(
        filter((res: HttpResponse<ITransactionProperty[]>) => res.ok),
        map((res: HttpResponse<ITransactionProperty[]>) => res.body)
      )
      .subscribe(
        (res: ITransactionProperty[]) => {
          this.transactionProperties = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTransactionProperties();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransactionProperty) {
    return item.id;
  }

  registerChangeInTransactionProperties() {
    this.eventSubscriber = this.eventManager.subscribe('transactionPropertyListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
