import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMeansofpayment } from 'app/shared/model/meansofpayment.model';
import { AccountService } from 'app/core';
import { MeansofpaymentService } from './meansofpayment.service';

@Component({
  selector: 'jhi-meansofpayment',
  templateUrl: './meansofpayment.component.html'
})
export class MeansofpaymentComponent implements OnInit, OnDestroy {
  meansofpayments: IMeansofpayment[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected meansofpaymentService: MeansofpaymentService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.meansofpaymentService
      .query()
      .pipe(
        filter((res: HttpResponse<IMeansofpayment[]>) => res.ok),
        map((res: HttpResponse<IMeansofpayment[]>) => res.body)
      )
      .subscribe(
        (res: IMeansofpayment[]) => {
          this.meansofpayments = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInMeansofpayments();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IMeansofpayment) {
    return item.id;
  }

  registerChangeInMeansofpayments() {
    this.eventSubscriber = this.eventManager.subscribe('meansofpaymentListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
