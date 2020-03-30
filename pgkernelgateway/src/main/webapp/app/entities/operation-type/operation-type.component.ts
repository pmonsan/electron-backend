import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOperationType } from 'app/shared/model/operation-type.model';
import { AccountService } from 'app/core';
import { OperationTypeService } from './operation-type.service';

@Component({
  selector: 'jhi-operation-type',
  templateUrl: './operation-type.component.html'
})
export class OperationTypeComponent implements OnInit, OnDestroy {
  operationTypes: IOperationType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected operationTypeService: OperationTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.operationTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IOperationType[]>) => res.ok),
        map((res: HttpResponse<IOperationType[]>) => res.body)
      )
      .subscribe(
        (res: IOperationType[]) => {
          this.operationTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInOperationTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOperationType) {
    return item.id;
  }

  registerChangeInOperationTypes() {
    this.eventSubscriber = this.eventManager.subscribe('operationTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
