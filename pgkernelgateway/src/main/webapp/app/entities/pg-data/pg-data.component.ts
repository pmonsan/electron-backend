import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgData } from 'app/shared/model/pg-data.model';
import { AccountService } from 'app/core';
import { PgDataService } from './pg-data.service';

@Component({
  selector: 'jhi-pg-data',
  templateUrl: './pg-data.component.html'
})
export class PgDataComponent implements OnInit, OnDestroy {
  pgData: IPgData[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgDataService: PgDataService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgDataService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgData[]>) => res.ok),
        map((res: HttpResponse<IPgData[]>) => res.body)
      )
      .subscribe(
        (res: IPgData[]) => {
          this.pgData = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgData();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgData) {
    return item.id;
  }

  registerChangeInPgData() {
    this.eventSubscriber = this.eventManager.subscribe('pgDataListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
