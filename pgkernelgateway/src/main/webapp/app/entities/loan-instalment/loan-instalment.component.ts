import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILoanInstalment } from 'app/shared/model/loan-instalment.model';
import { AccountService } from 'app/core';
import { LoanInstalmentService } from './loan-instalment.service';

@Component({
  selector: 'jhi-loan-instalment',
  templateUrl: './loan-instalment.component.html'
})
export class LoanInstalmentComponent implements OnInit, OnDestroy {
  loanInstalments: ILoanInstalment[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected loanInstalmentService: LoanInstalmentService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.loanInstalmentService
      .query()
      .pipe(
        filter((res: HttpResponse<ILoanInstalment[]>) => res.ok),
        map((res: HttpResponse<ILoanInstalment[]>) => res.body)
      )
      .subscribe(
        (res: ILoanInstalment[]) => {
          this.loanInstalments = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLoanInstalments();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILoanInstalment) {
    return item.id;
  }

  registerChangeInLoanInstalments() {
    this.eventSubscriber = this.eventManager.subscribe('loanInstalmentListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
