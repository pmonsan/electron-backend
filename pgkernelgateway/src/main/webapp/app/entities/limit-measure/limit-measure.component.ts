import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILimitMeasure } from 'app/shared/model/limit-measure.model';
import { AccountService } from 'app/core';
import { LimitMeasureService } from './limit-measure.service';

@Component({
  selector: 'jhi-limit-measure',
  templateUrl: './limit-measure.component.html'
})
export class LimitMeasureComponent implements OnInit, OnDestroy {
  limitMeasures: ILimitMeasure[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected limitMeasureService: LimitMeasureService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.limitMeasureService
      .query()
      .pipe(
        filter((res: HttpResponse<ILimitMeasure[]>) => res.ok),
        map((res: HttpResponse<ILimitMeasure[]>) => res.body)
      )
      .subscribe(
        (res: ILimitMeasure[]) => {
          this.limitMeasures = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLimitMeasures();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILimitMeasure) {
    return item.id;
  }

  registerChangeInLimitMeasures() {
    this.eventSubscriber = this.eventManager.subscribe('limitMeasureListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
