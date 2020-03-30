import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgMessageStatus } from 'app/shared/model/pg-message-status.model';
import { AccountService } from 'app/core';
import { PgMessageStatusService } from './pg-message-status.service';

@Component({
  selector: 'jhi-pg-message-status',
  templateUrl: './pg-message-status.component.html'
})
export class PgMessageStatusComponent implements OnInit, OnDestroy {
  pgMessageStatuses: IPgMessageStatus[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgMessageStatusService: PgMessageStatusService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgMessageStatusService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgMessageStatus[]>) => res.ok),
        map((res: HttpResponse<IPgMessageStatus[]>) => res.body)
      )
      .subscribe(
        (res: IPgMessageStatus[]) => {
          this.pgMessageStatuses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgMessageStatuses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgMessageStatus) {
    return item.id;
  }

  registerChangeInPgMessageStatuses() {
    this.eventSubscriber = this.eventManager.subscribe('pgMessageStatusListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
