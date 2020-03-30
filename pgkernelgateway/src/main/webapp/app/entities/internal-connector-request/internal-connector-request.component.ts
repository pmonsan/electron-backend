import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInternalConnectorRequest } from 'app/shared/model/internal-connector-request.model';
import { AccountService } from 'app/core';
import { InternalConnectorRequestService } from './internal-connector-request.service';

@Component({
  selector: 'jhi-internal-connector-request',
  templateUrl: './internal-connector-request.component.html'
})
export class InternalConnectorRequestComponent implements OnInit, OnDestroy {
  internalConnectorRequests: IInternalConnectorRequest[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected internalConnectorRequestService: InternalConnectorRequestService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.internalConnectorRequestService
      .query()
      .pipe(
        filter((res: HttpResponse<IInternalConnectorRequest[]>) => res.ok),
        map((res: HttpResponse<IInternalConnectorRequest[]>) => res.body)
      )
      .subscribe(
        (res: IInternalConnectorRequest[]) => {
          this.internalConnectorRequests = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInternalConnectorRequests();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInternalConnectorRequest) {
    return item.id;
  }

  registerChangeInInternalConnectorRequests() {
    this.eventSubscriber = this.eventManager.subscribe('internalConnectorRequestListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
