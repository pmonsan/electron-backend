import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgResponseCode } from 'app/shared/model/pg-response-code.model';
import { AccountService } from 'app/core';
import { PgResponseCodeService } from './pg-response-code.service';

@Component({
  selector: 'jhi-pg-response-code',
  templateUrl: './pg-response-code.component.html'
})
export class PgResponseCodeComponent implements OnInit, OnDestroy {
  pgResponseCodes: IPgResponseCode[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgResponseCodeService: PgResponseCodeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgResponseCodeService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgResponseCode[]>) => res.ok),
        map((res: HttpResponse<IPgResponseCode[]>) => res.body)
      )
      .subscribe(
        (res: IPgResponseCode[]) => {
          this.pgResponseCodes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgResponseCodes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgResponseCode) {
    return item.id;
  }

  registerChangeInPgResponseCodes() {
    this.eventSubscriber = this.eventManager.subscribe('pgResponseCodeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
