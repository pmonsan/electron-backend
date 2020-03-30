import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILimitType } from 'app/shared/model/limit-type.model';
import { AccountService } from 'app/core';
import { LimitTypeService } from './limit-type.service';

@Component({
  selector: 'jhi-limit-type',
  templateUrl: './limit-type.component.html'
})
export class LimitTypeComponent implements OnInit, OnDestroy {
  limitTypes: ILimitType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected limitTypeService: LimitTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.limitTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<ILimitType[]>) => res.ok),
        map((res: HttpResponse<ILimitType[]>) => res.body)
      )
      .subscribe(
        (res: ILimitType[]) => {
          this.limitTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLimitTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILimitType) {
    return item.id;
  }

  registerChangeInLimitTypes() {
    this.eventSubscriber = this.eventManager.subscribe('limitTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
