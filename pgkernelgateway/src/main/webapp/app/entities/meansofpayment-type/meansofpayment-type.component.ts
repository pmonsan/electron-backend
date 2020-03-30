import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMeansofpaymentType } from 'app/shared/model/meansofpayment-type.model';
import { AccountService } from 'app/core';
import { MeansofpaymentTypeService } from './meansofpayment-type.service';

@Component({
  selector: 'jhi-meansofpayment-type',
  templateUrl: './meansofpayment-type.component.html'
})
export class MeansofpaymentTypeComponent implements OnInit, OnDestroy {
  meansofpaymentTypes: IMeansofpaymentType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected meansofpaymentTypeService: MeansofpaymentTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.meansofpaymentTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IMeansofpaymentType[]>) => res.ok),
        map((res: HttpResponse<IMeansofpaymentType[]>) => res.body)
      )
      .subscribe(
        (res: IMeansofpaymentType[]) => {
          this.meansofpaymentTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMeansofpaymentTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMeansofpaymentType) {
    return item.id;
  }

  registerChangeInMeansofpaymentTypes() {
    this.eventSubscriber = this.eventManager.subscribe('meansofpaymentTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
