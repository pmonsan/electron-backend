import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserConnection } from 'app/shared/model/user-connection.model';
import { AccountService } from 'app/core';
import { UserConnectionService } from './user-connection.service';

@Component({
  selector: 'jhi-user-connection',
  templateUrl: './user-connection.component.html'
})
export class UserConnectionComponent implements OnInit, OnDestroy {
  userConnections: IUserConnection[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected userConnectionService: UserConnectionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.userConnectionService
      .query()
      .pipe(
        filter((res: HttpResponse<IUserConnection[]>) => res.ok),
        map((res: HttpResponse<IUserConnection[]>) => res.body)
      )
      .subscribe(
        (res: IUserConnection[]) => {
          this.userConnections = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUserConnections();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUserConnection) {
    return item.id;
  }

  registerChangeInUserConnections() {
    this.eventSubscriber = this.eventManager.subscribe('userConnectionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
