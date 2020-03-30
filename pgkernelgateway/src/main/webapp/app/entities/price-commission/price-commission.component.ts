import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPriceCommission } from 'app/shared/model/price-commission.model';
import { AccountService } from 'app/core';
import { PriceCommissionService } from './price-commission.service';

@Component({
  selector: 'jhi-price-commission',
  templateUrl: './price-commission.component.html'
})
export class PriceCommissionComponent implements OnInit, OnDestroy {
  priceCommissions: IPriceCommission[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected priceCommissionService: PriceCommissionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.priceCommissionService
      .query()
      .pipe(
        filter((res: HttpResponse<IPriceCommission[]>) => res.ok),
        map((res: HttpResponse<IPriceCommission[]>) => res.body)
      )
      .subscribe(
        (res: IPriceCommission[]) => {
          this.priceCommissions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPriceCommissions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPriceCommission) {
    return item.id;
  }

  registerChangeInPriceCommissions() {
    this.eventSubscriber = this.eventManager.subscribe('priceCommissionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
