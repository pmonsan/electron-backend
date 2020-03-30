import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOperationStatus } from 'app/shared/model/operation-status.model';
import { AccountService } from 'app/core';
import { OperationStatusService } from './operation-status.service';

@Component({
  selector: 'jhi-operation-status',
  templateUrl: './operation-status.component.html'
})
export class OperationStatusComponent implements OnInit, OnDestroy {
  operationStatuses: IOperationStatus[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected operationStatusService: OperationStatusService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.operationStatusService
      .query()
      .pipe(
        filter((res: HttpResponse<IOperationStatus[]>) => res.ok),
        map((res: HttpResponse<IOperationStatus[]>) => res.body)
      )
      .subscribe(
        (res: IOperationStatus[]) => {
          this.operationStatuses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInOperationStatuses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOperationStatus) {
    return item.id;
  }

  registerChangeInOperationStatuses() {
    this.eventSubscriber = this.eventManager.subscribe('operationStatusListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
