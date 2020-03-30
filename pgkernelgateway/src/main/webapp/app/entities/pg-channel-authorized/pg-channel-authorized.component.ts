import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgChannelAuthorized } from 'app/shared/model/pg-channel-authorized.model';
import { AccountService } from 'app/core';
import { PgChannelAuthorizedService } from './pg-channel-authorized.service';

@Component({
  selector: 'jhi-pg-channel-authorized',
  templateUrl: './pg-channel-authorized.component.html'
})
export class PgChannelAuthorizedComponent implements OnInit, OnDestroy {
  pgChannelAuthorizeds: IPgChannelAuthorized[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgChannelAuthorizedService: PgChannelAuthorizedService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgChannelAuthorizedService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgChannelAuthorized[]>) => res.ok),
        map((res: HttpResponse<IPgChannelAuthorized[]>) => res.body)
      )
      .subscribe(
        (res: IPgChannelAuthorized[]) => {
          this.pgChannelAuthorizeds = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgChannelAuthorizeds();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgChannelAuthorized) {
    return item.id;
  }

  registerChangeInPgChannelAuthorizeds() {
    this.eventSubscriber = this.eventManager.subscribe('pgChannelAuthorizedListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
