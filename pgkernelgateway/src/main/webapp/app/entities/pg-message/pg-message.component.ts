import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgMessage } from 'app/shared/model/pg-message.model';
import { AccountService } from 'app/core';
import { PgMessageService } from './pg-message.service';

@Component({
  selector: 'jhi-pg-message',
  templateUrl: './pg-message.component.html'
})
export class PgMessageComponent implements OnInit, OnDestroy {
  pgMessages: IPgMessage[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgMessageService: PgMessageService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgMessageService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgMessage[]>) => res.ok),
        map((res: HttpResponse<IPgMessage[]>) => res.body)
      )
      .subscribe(
        (res: IPgMessage[]) => {
          this.pgMessages = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgMessages();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgMessage) {
    return item.id;
  }

  registerChangeInPgMessages() {
    this.eventSubscriber = this.eventManager.subscribe('pgMessageListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
