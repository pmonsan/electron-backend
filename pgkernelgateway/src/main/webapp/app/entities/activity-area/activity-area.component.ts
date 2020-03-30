import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IActivityArea } from 'app/shared/model/activity-area.model';
import { AccountService } from 'app/core';
import { ActivityAreaService } from './activity-area.service';

@Component({
  selector: 'jhi-activity-area',
  templateUrl: './activity-area.component.html'
})
export class ActivityAreaComponent implements OnInit, OnDestroy {
  activityAreas: IActivityArea[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected activityAreaService: ActivityAreaService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.activityAreaService
      .query()
      .pipe(
        filter((res: HttpResponse<IActivityArea[]>) => res.ok),
        map((res: HttpResponse<IActivityArea[]>) => res.body)
      )
      .subscribe(
        (res: IActivityArea[]) => {
          this.activityAreas = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInActivityAreas();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IActivityArea) {
    return item.id;
  }

  registerChangeInActivityAreas() {
    this.eventSubscriber = this.eventManager.subscribe('activityAreaListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
