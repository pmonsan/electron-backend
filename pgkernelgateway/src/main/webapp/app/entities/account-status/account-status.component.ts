import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAccountStatus } from 'app/shared/model/account-status.model';
import { AccountService } from 'app/core';
import { AccountStatusService } from './account-status.service';

@Component({
  selector: 'jhi-account-status',
  templateUrl: './account-status.component.html'
})
export class AccountStatusComponent implements OnInit, OnDestroy {
  accountStatuses: IAccountStatus[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected accountStatusService: AccountStatusService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.accountStatusService
      .query()
      .pipe(
        filter((res: HttpResponse<IAccountStatus[]>) => res.ok),
        map((res: HttpResponse<IAccountStatus[]>) => res.body)
      )
      .subscribe(
        (res: IAccountStatus[]) => {
          this.accountStatuses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAccountStatuses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAccountStatus) {
    return item.id;
  }

  registerChangeInAccountStatuses() {
    this.eventSubscriber = this.eventManager.subscribe('accountStatusListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
