import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFeature } from 'app/shared/model/feature.model';
import { AccountService } from 'app/core';
import { FeatureService } from './feature.service';

@Component({
  selector: 'jhi-feature',
  templateUrl: './feature.component.html'
})
export class FeatureComponent implements OnInit, OnDestroy {
  features: IFeature[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected featureService: FeatureService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.featureService
      .query()
      .pipe(
        filter((res: HttpResponse<IFeature[]>) => res.ok),
        map((res: HttpResponse<IFeature[]>) => res.body)
      )
      .subscribe(
        (res: IFeature[]) => {
          this.features = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFeatures();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFeature) {
    return item.id;
  }

  registerChangeInFeatures() {
    this.eventSubscriber = this.eventManager.subscribe('featureListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
