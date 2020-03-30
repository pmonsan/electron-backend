import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgApplication } from 'app/shared/model/pg-application.model';
import { AccountService } from 'app/core';
import { PgApplicationService } from './pg-application.service';

@Component({
  selector: 'jhi-pg-application',
  templateUrl: './pg-application.component.html'
})
export class PgApplicationComponent implements OnInit, OnDestroy {
  pgApplications: IPgApplication[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgApplicationService: PgApplicationService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgApplicationService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgApplication[]>) => res.ok),
        map((res: HttpResponse<IPgApplication[]>) => res.body)
      )
      .subscribe(
        (res: IPgApplication[]) => {
          this.pgApplications = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgApplications();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgApplication) {
    return item.id;
  }

  registerChangeInPgApplications() {
    this.eventSubscriber = this.eventManager.subscribe('pgApplicationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
