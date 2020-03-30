import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgApplicationService } from 'app/shared/model/pg-application-service.model';
import { AccountService } from 'app/core';
import { PgApplicationServiceService } from './pg-application-service.service';

@Component({
  selector: 'jhi-pg-application-service',
  templateUrl: './pg-application-service.component.html'
})
export class PgApplicationServiceComponent implements OnInit, OnDestroy {
  pgApplicationServices: IPgApplicationService[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgApplicationServiceService: PgApplicationServiceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgApplicationServiceService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgApplicationService[]>) => res.ok),
        map((res: HttpResponse<IPgApplicationService[]>) => res.body)
      )
      .subscribe(
        (res: IPgApplicationService[]) => {
          this.pgApplicationServices = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgApplicationServices();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgApplicationService) {
    return item.id;
  }

  registerChangeInPgApplicationServices() {
    this.eventSubscriber = this.eventManager.subscribe('pgApplicationServiceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
