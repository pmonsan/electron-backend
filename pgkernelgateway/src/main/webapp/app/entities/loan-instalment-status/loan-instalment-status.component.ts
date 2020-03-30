import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILoanInstalmentStatus } from 'app/shared/model/loan-instalment-status.model';
import { AccountService } from 'app/core';
import { LoanInstalmentStatusService } from './loan-instalment-status.service';

@Component({
  selector: 'jhi-loan-instalment-status',
  templateUrl: './loan-instalment-status.component.html'
})
export class LoanInstalmentStatusComponent implements OnInit, OnDestroy {
  loanInstalmentStatuses: ILoanInstalmentStatus[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected loanInstalmentStatusService: LoanInstalmentStatusService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.loanInstalmentStatusService
      .query()
      .pipe(
        filter((res: HttpResponse<ILoanInstalmentStatus[]>) => res.ok),
        map((res: HttpResponse<ILoanInstalmentStatus[]>) => res.body)
      )
      .subscribe(
        (res: ILoanInstalmentStatus[]) => {
          this.loanInstalmentStatuses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLoanInstalmentStatuses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILoanInstalmentStatus) {
    return item.id;
  }

  registerChangeInLoanInstalmentStatuses() {
    this.eventSubscriber = this.eventManager.subscribe('loanInstalmentStatusListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
