import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPg8583Callback } from 'app/shared/model/pg-8583-callback.model';
import { AccountService } from 'app/core';
import { Pg8583CallbackService } from './pg-8583-callback.service';

@Component({
  selector: 'jhi-pg-8583-callback',
  templateUrl: './pg-8583-callback.component.html'
})
export class Pg8583CallbackComponent implements OnInit, OnDestroy {
  pg8583Callbacks: IPg8583Callback[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pg8583CallbackService: Pg8583CallbackService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pg8583CallbackService
      .query()
      .pipe(
        filter((res: HttpResponse<IPg8583Callback[]>) => res.ok),
        map((res: HttpResponse<IPg8583Callback[]>) => res.body)
      )
      .subscribe(
        (res: IPg8583Callback[]) => {
          this.pg8583Callbacks = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPg8583Callbacks();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPg8583Callback) {
    return item.id;
  }

  registerChangeInPg8583Callbacks() {
    this.eventSubscriber = this.eventManager.subscribe('pg8583CallbackListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
