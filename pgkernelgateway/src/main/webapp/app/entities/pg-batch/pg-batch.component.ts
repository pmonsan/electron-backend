import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgBatch } from 'app/shared/model/pg-batch.model';
import { AccountService } from 'app/core';
import { PgBatchService } from './pg-batch.service';

@Component({
  selector: 'jhi-pg-batch',
  templateUrl: './pg-batch.component.html'
})
export class PgBatchComponent implements OnInit, OnDestroy {
  pgBatches: IPgBatch[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgBatchService: PgBatchService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgBatchService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgBatch[]>) => res.ok),
        map((res: HttpResponse<IPgBatch[]>) => res.body)
      )
      .subscribe(
        (res: IPgBatch[]) => {
          this.pgBatches = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgBatches();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgBatch) {
    return item.id;
  }

  registerChangeInPgBatches() {
    this.eventSubscriber = this.eventManager.subscribe('pgBatchListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
