import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPgMessageModel } from 'app/shared/model/pg-message-model.model';
import { AccountService } from 'app/core';
import { PgMessageModelService } from './pg-message-model.service';

@Component({
  selector: 'jhi-pg-message-model',
  templateUrl: './pg-message-model.component.html'
})
export class PgMessageModelComponent implements OnInit, OnDestroy {
  pgMessageModels: IPgMessageModel[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected pgMessageModelService: PgMessageModelService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.pgMessageModelService
      .query()
      .pipe(
        filter((res: HttpResponse<IPgMessageModel[]>) => res.ok),
        map((res: HttpResponse<IPgMessageModel[]>) => res.body)
      )
      .subscribe(
        (res: IPgMessageModel[]) => {
          this.pgMessageModels = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPgMessageModels();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPgMessageModel) {
    return item.id;
  }

  registerChangeInPgMessageModels() {
    this.eventSubscriber = this.eventManager.subscribe('pgMessageModelListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
