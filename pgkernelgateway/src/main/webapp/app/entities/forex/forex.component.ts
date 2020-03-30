import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IForex } from 'app/shared/model/forex.model';
import { AccountService } from 'app/core';
import { ForexService } from './forex.service';

@Component({
  selector: 'jhi-forex',
  templateUrl: './forex.component.html'
})
export class ForexComponent implements OnInit, OnDestroy {
  forexes: IForex[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected forexService: ForexService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.forexService
      .query()
      .pipe(
        filter((res: HttpResponse<IForex[]>) => res.ok),
        map((res: HttpResponse<IForex[]>) => res.body)
      )
      .subscribe(
        (res: IForex[]) => {
          this.forexes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInForexes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IForex) {
    return item.id;
  }

  registerChangeInForexes() {
    this.eventSubscriber = this.eventManager.subscribe('forexListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
