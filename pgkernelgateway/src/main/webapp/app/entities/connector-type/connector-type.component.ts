import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IConnectorType } from 'app/shared/model/connector-type.model';
import { AccountService } from 'app/core';
import { ConnectorTypeService } from './connector-type.service';

@Component({
  selector: 'jhi-connector-type',
  templateUrl: './connector-type.component.html'
})
export class ConnectorTypeComponent implements OnInit, OnDestroy {
  connectorTypes: IConnectorType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected connectorTypeService: ConnectorTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.connectorTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IConnectorType[]>) => res.ok),
        map((res: HttpResponse<IConnectorType[]>) => res.body)
      )
      .subscribe(
        (res: IConnectorType[]) => {
          this.connectorTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInConnectorTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IConnectorType) {
    return item.id;
  }

  registerChangeInConnectorTypes() {
    this.eventSubscriber = this.eventManager.subscribe('connectorTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
