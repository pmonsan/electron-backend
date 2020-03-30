import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IServiceLimit } from 'app/shared/model/service-limit.model';
import { AccountService } from 'app/core';
import { ServiceLimitService } from './service-limit.service';

@Component({
  selector: 'jhi-service-limit',
  templateUrl: './service-limit.component.html'
})
export class ServiceLimitComponent implements OnInit, OnDestroy {
  serviceLimits: IServiceLimit[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected serviceLimitService: ServiceLimitService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.serviceLimitService
      .query()
      .pipe(
        filter((res: HttpResponse<IServiceLimit[]>) => res.ok),
        map((res: HttpResponse<IServiceLimit[]>) => res.body)
      )
      .subscribe(
        (res: IServiceLimit[]) => {
          this.serviceLimits = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInServiceLimits();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IServiceLimit) {
    return item.id;
  }

  registerChangeInServiceLimits() {
    this.eventSubscriber = this.eventManager.subscribe('serviceLimitListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
