import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPricePlan } from 'app/shared/model/price-plan.model';
import { AccountService } from 'app/core';
import { PricePlanService } from './price-plan.service';

@Component({
  selector: 'jhi-price-plan',
  templateUrl: './price-plan.component.html'
})
export class PricePlanComponent implements OnInit, OnDestroy {
  pricePlans: IPricePlan[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pricePlanService: PricePlanService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pricePlanService
      .query()
      .pipe(
        filter((res: HttpResponse<IPricePlan[]>) => res.ok),
        map((res: HttpResponse<IPricePlan[]>) => res.body)
      )
      .subscribe(
        (res: IPricePlan[]) => {
          this.pricePlans = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPricePlans();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPricePlan) {
    return item.id;
  }

  registerChangeInPricePlans() {
    this.eventSubscriber = this.eventManager.subscribe('pricePlanListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
