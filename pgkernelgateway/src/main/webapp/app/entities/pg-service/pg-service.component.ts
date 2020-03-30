import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgService } from 'app/shared/model/pg-service.model';
import { AccountService } from 'app/core';
import { PgServiceService } from './pg-service.service';

@Component({
  selector: 'jhi-pg-service',
  templateUrl: './pg-service.component.html'
})
export class PgServiceComponent implements OnInit, OnDestroy {
  pgServices: IPgService[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgServiceService: PgServiceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgServiceService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgService[]>) => res.ok),
        map((res: HttpResponse<IPgService[]>) => res.body)
      )
      .subscribe(
        (res: IPgService[]) => {
          this.pgServices = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgServices();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgService) {
    return item.id;
  }

  registerChangeInPgServices() {
    this.eventSubscriber = this.eventManager.subscribe('pgServiceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
