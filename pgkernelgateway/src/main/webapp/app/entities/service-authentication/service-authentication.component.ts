import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IServiceAuthentication } from 'app/shared/model/service-authentication.model';
import { AccountService } from 'app/core';
import { ServiceAuthenticationService } from './service-authentication.service';

@Component({
  selector: 'jhi-service-authentication',
  templateUrl: './service-authentication.component.html'
})
export class ServiceAuthenticationComponent implements OnInit, OnDestroy {
  serviceAuthentications: IServiceAuthentication[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected serviceAuthenticationService: ServiceAuthenticationService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.serviceAuthenticationService
      .query()
      .pipe(
        filter((res: HttpResponse<IServiceAuthentication[]>) => res.ok),
        map((res: HttpResponse<IServiceAuthentication[]>) => res.body)
      )
      .subscribe(
        (res: IServiceAuthentication[]) => {
          this.serviceAuthentications = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInServiceAuthentications();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IServiceAuthentication) {
    return item.id;
  }

  registerChangeInServiceAuthentications() {
    this.eventSubscriber = this.eventManager.subscribe('serviceAuthenticationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
