import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IInternalConnectorStatus } from 'app/shared/model/internal-connector-status.model';
import { AccountService } from 'app/core';
import { InternalConnectorStatusService } from './internal-connector-status.service';

@Component({
  selector: 'jhi-internal-connector-status',
  templateUrl: './internal-connector-status.component.html'
})
export class InternalConnectorStatusComponent implements OnInit, OnDestroy {
  internalConnectorStatuses: IInternalConnectorStatus[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected internalConnectorStatusService: InternalConnectorStatusService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.internalConnectorStatusService
      .query()
      .pipe(
        filter((res: HttpResponse<IInternalConnectorStatus[]>) => res.ok),
        map((res: HttpResponse<IInternalConnectorStatus[]>) => res.body)
      )
      .subscribe(
        (res: IInternalConnectorStatus[]) => {
          this.internalConnectorStatuses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInInternalConnectorStatuses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IInternalConnectorStatus) {
    return item.id;
  }

  registerChangeInInternalConnectorStatuses() {
    this.eventSubscriber = this.eventManager.subscribe('internalConnectorStatusListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
