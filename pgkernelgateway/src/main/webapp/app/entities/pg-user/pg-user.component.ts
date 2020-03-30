import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgUser } from 'app/shared/model/pg-user.model';
import { AccountService } from 'app/core';
import { PgUserService } from './pg-user.service';

@Component({
  selector: 'jhi-pg-user',
  templateUrl: './pg-user.component.html'
})
export class PgUserComponent implements OnInit, OnDestroy {
  pgUsers: IPgUser[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgUserService: PgUserService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgUserService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgUser[]>) => res.ok),
        map((res: HttpResponse<IPgUser[]>) => res.body)
      )
      .subscribe(
        (res: IPgUser[]) => {
          this.pgUsers = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgUsers();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgUser) {
    return item.id;
  }

  registerChangeInPgUsers() {
    this.eventSubscriber = this.eventManager.subscribe('pgUserListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
