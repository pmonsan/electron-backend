import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IConnector } from 'app/shared/model/connector.model';
import { AccountService } from 'app/core';
import { ConnectorService } from './connector.service';

@Component({
  selector: 'jhi-connector',
  templateUrl: './connector.component.html'
})
export class ConnectorComponent implements OnInit, OnDestroy {
  connectors: IConnector[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected connectorService: ConnectorService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.connectorService
      .query()
      .pipe(
        filter((res: HttpResponse<IConnector[]>) => res.ok),
        map((res: HttpResponse<IConnector[]>) => res.body)
      )
      .subscribe(
        (res: IConnector[]) => {
          this.connectors = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInConnectors();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IConnector) {
    return item.id;
  }

  registerChangeInConnectors() {
    this.eventSubscriber = this.eventManager.subscribe('connectorListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
