import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPg8583Request } from 'app/shared/model/pg-8583-request.model';
import { AccountService } from 'app/core';
import { Pg8583RequestService } from './pg-8583-request.service';

@Component({
  selector: 'jhi-pg-8583-request',
  templateUrl: './pg-8583-request.component.html'
})
export class Pg8583RequestComponent implements OnInit, OnDestroy {
  pg8583Requests: IPg8583Request[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pg8583RequestService: Pg8583RequestService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pg8583RequestService
      .query()
      .pipe(
        filter((res: HttpResponse<IPg8583Request[]>) => res.ok),
        map((res: HttpResponse<IPg8583Request[]>) => res.body)
      )
      .subscribe(
        (res: IPg8583Request[]) => {
          this.pg8583Requests = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPg8583Requests();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPg8583Request) {
    return item.id;
  }

  registerChangeInPg8583Requests() {
    this.eventSubscriber = this.eventManager.subscribe('pg8583RequestListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
