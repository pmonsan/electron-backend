import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IServiceIntegration } from 'app/shared/model/service-integration.model';
import { AccountService } from 'app/core';
import { ServiceIntegrationService } from './service-integration.service';

@Component({
  selector: 'jhi-service-integration',
  templateUrl: './service-integration.component.html'
})
export class ServiceIntegrationComponent implements OnInit, OnDestroy {
  serviceIntegrations: IServiceIntegration[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected serviceIntegrationService: ServiceIntegrationService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.serviceIntegrationService
      .query()
      .pipe(
        filter((res: HttpResponse<IServiceIntegration[]>) => res.ok),
        map((res: HttpResponse<IServiceIntegration[]>) => res.body)
      )
      .subscribe(
        (res: IServiceIntegration[]) => {
          this.serviceIntegrations = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInServiceIntegrations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IServiceIntegration) {
    return item.id;
  }

  registerChangeInServiceIntegrations() {
    this.eventSubscriber = this.eventManager.subscribe('serviceIntegrationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
