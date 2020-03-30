import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPeriodicity } from 'app/shared/model/periodicity.model';
import { AccountService } from 'app/core';
import { PeriodicityService } from './periodicity.service';

@Component({
  selector: 'jhi-periodicity',
  templateUrl: './periodicity.component.html'
})
export class PeriodicityComponent implements OnInit, OnDestroy {
  periodicities: IPeriodicity[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected periodicityService: PeriodicityService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.periodicityService
      .query()
      .pipe(
        filter((res: HttpResponse<IPeriodicity[]>) => res.ok),
        map((res: HttpResponse<IPeriodicity[]>) => res.body)
      )
      .subscribe(
        (res: IPeriodicity[]) => {
          this.periodicities = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPeriodicities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPeriodicity) {
    return item.id;
  }

  registerChangeInPeriodicities() {
    this.eventSubscriber = this.eventManager.subscribe('periodicityListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
