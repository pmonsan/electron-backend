import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgMessageModelData } from 'app/shared/model/pg-message-model-data.model';
import { AccountService } from 'app/core';
import { PgMessageModelDataService } from './pg-message-model-data.service';

@Component({
  selector: 'jhi-pg-message-model-data',
  templateUrl: './pg-message-model-data.component.html'
})
export class PgMessageModelDataComponent implements OnInit, OnDestroy {
  pgMessageModelData: IPgMessageModelData[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgMessageModelDataService: PgMessageModelDataService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgMessageModelDataService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgMessageModelData[]>) => res.ok),
        map((res: HttpResponse<IPgMessageModelData[]>) => res.body)
      )
      .subscribe(
        (res: IPgMessageModelData[]) => {
          this.pgMessageModelData = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgMessageModelData();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgMessageModelData) {
    return item.id;
  }

  registerChangeInPgMessageModelData() {
    this.eventSubscriber = this.eventManager.subscribe('pgMessageModelDataListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
