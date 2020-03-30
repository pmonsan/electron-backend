import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOperation } from 'app/shared/model/operation.model';
import { AccountService } from 'app/core';
import { OperationService } from './operation.service';

@Component({
  selector: 'jhi-operation',
  templateUrl: './operation.component.html'
})
export class OperationComponent implements OnInit, OnDestroy {
  operations: IOperation[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected operationService: OperationService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.operationService
      .query()
      .pipe(
        filter((res: HttpResponse<IOperation[]>) => res.ok),
        map((res: HttpResponse<IOperation[]>) => res.body)
      )
      .subscribe(
        (res: IOperation[]) => {
          this.operations = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInOperations();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IOperation) {
    return item.id;
  }

  registerChangeInOperations() {
    this.eventSubscriber = this.eventManager.subscribe('operationListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
