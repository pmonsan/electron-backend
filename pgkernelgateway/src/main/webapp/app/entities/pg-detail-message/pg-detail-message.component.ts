import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgDetailMessage } from 'app/shared/model/pg-detail-message.model';
import { AccountService } from 'app/core';
import { PgDetailMessageService } from './pg-detail-message.service';

@Component({
  selector: 'jhi-pg-detail-message',
  templateUrl: './pg-detail-message.component.html'
})
export class PgDetailMessageComponent implements OnInit, OnDestroy {
  pgDetailMessages: IPgDetailMessage[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgDetailMessageService: PgDetailMessageService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgDetailMessageService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgDetailMessage[]>) => res.ok),
        map((res: HttpResponse<IPgDetailMessage[]>) => res.body)
      )
      .subscribe(
        (res: IPgDetailMessage[]) => {
          this.pgDetailMessages = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgDetailMessages();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgDetailMessage) {
    return item.id;
  }

  registerChangeInPgDetailMessages() {
    this.eventSubscriber = this.eventManager.subscribe('pgDetailMessageListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
