import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAccountType } from 'app/shared/model/account-type.model';
import { AccountService } from 'app/core';
import { AccountTypeService } from './account-type.service';

@Component({
  selector: 'jhi-account-type',
  templateUrl: './account-type.component.html'
})
export class AccountTypeComponent implements OnInit, OnDestroy {
  accountTypes: IAccountType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected accountTypeService: AccountTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.accountTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IAccountType[]>) => res.ok),
        map((res: HttpResponse<IAccountType[]>) => res.body)
      )
      .subscribe(
        (res: IAccountType[]) => {
          this.accountTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAccountTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAccountType) {
    return item.id;
  }

  registerChangeInAccountTypes() {
    this.eventSubscriber = this.eventManager.subscribe('accountTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
