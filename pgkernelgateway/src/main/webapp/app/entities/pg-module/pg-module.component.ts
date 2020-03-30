import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgModule } from 'app/shared/model/pg-module.model';
import { AccountService } from 'app/core';
import { PgModuleService } from './pg-module.service';

@Component({
  selector: 'jhi-pg-module',
  templateUrl: './pg-module.component.html'
})
export class PgModuleComponent implements OnInit, OnDestroy {
  pgModules: IPgModule[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgModuleService: PgModuleService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgModuleService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgModule[]>) => res.ok),
        map((res: HttpResponse<IPgModule[]>) => res.body)
      )
      .subscribe(
        (res: IPgModule[]) => {
          this.pgModules = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgModules();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgModule) {
    return item.id;
  }

  registerChangeInPgModules() {
    this.eventSubscriber = this.eventManager.subscribe('pgModuleListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
