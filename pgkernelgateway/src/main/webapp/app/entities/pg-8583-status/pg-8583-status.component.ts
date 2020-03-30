import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPg8583Status } from 'app/shared/model/pg-8583-status.model';
import { AccountService } from 'app/core';
import { Pg8583StatusService } from './pg-8583-status.service';

@Component({
  selector: 'jhi-pg-8583-status',
  templateUrl: './pg-8583-status.component.html'
})
export class Pg8583StatusComponent implements OnInit, OnDestroy {
  pg8583Statuses: IPg8583Status[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pg8583StatusService: Pg8583StatusService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pg8583StatusService
      .query()
      .pipe(
        filter((res: HttpResponse<IPg8583Status[]>) => res.ok),
        map((res: HttpResponse<IPg8583Status[]>) => res.body)
      )
      .subscribe(
        (res: IPg8583Status[]) => {
          this.pg8583Statuses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPg8583Statuses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPg8583Status) {
    return item.id;
  }

  registerChangeInPg8583Statuses() {
    this.eventSubscriber = this.eventManager.subscribe('pg8583StatusListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
