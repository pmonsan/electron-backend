import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAccountFeature } from 'app/shared/model/account-feature.model';
import { AccountService } from 'app/core';
import { AccountFeatureService } from './account-feature.service';

@Component({
  selector: 'jhi-account-feature',
  templateUrl: './account-feature.component.html'
})
export class AccountFeatureComponent implements OnInit, OnDestroy {
  accountFeatures: IAccountFeature[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected accountFeatureService: AccountFeatureService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.accountFeatureService
      .query()
      .pipe(
        filter((res: HttpResponse<IAccountFeature[]>) => res.ok),
        map((res: HttpResponse<IAccountFeature[]>) => res.body)
      )
      .subscribe(
        (res: IAccountFeature[]) => {
          this.accountFeatures = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAccountFeatures();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAccountFeature) {
    return item.id;
  }

  registerChangeInAccountFeatures() {
    this.eventSubscriber = this.eventManager.subscribe('accountFeatureListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
