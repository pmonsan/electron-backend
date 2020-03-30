import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgChannel } from 'app/shared/model/pg-channel.model';
import { AccountService } from 'app/core';
import { PgChannelService } from './pg-channel.service';

@Component({
  selector: 'jhi-pg-channel',
  templateUrl: './pg-channel.component.html'
})
export class PgChannelComponent implements OnInit, OnDestroy {
  pgChannels: IPgChannel[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgChannelService: PgChannelService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgChannelService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgChannel[]>) => res.ok),
        map((res: HttpResponse<IPgChannel[]>) => res.body)
      )
      .subscribe(
        (res: IPgChannel[]) => {
          this.pgChannels = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgChannels();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgChannel) {
    return item.id;
  }

  registerChangeInPgChannels() {
    this.eventSubscriber = this.eventManager.subscribe('pgChannelListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
