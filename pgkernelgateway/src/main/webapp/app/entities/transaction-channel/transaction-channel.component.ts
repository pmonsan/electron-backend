import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITransactionChannel } from 'app/shared/model/transaction-channel.model';
import { AccountService } from 'app/core';
import { TransactionChannelService } from './transaction-channel.service';

@Component({
  selector: 'jhi-transaction-channel',
  templateUrl: './transaction-channel.component.html'
})
export class TransactionChannelComponent implements OnInit, OnDestroy {
  transactionChannels: ITransactionChannel[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected transactionChannelService: TransactionChannelService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.transactionChannelService
      .query()
      .pipe(
        filter((res: HttpResponse<ITransactionChannel[]>) => res.ok),
        map((res: HttpResponse<ITransactionChannel[]>) => res.body)
      )
      .subscribe(
        (res: ITransactionChannel[]) => {
          this.transactionChannels = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInTransactionChannels();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ITransactionChannel) {
    return item.id;
  }

  registerChangeInTransactionChannels() {
    this.eventSubscriber = this.eventManager.subscribe('transactionChannelListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
