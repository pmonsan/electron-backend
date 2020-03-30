import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDetailContract } from 'app/shared/model/detail-contract.model';
import { AccountService } from 'app/core';
import { DetailContractService } from './detail-contract.service';

@Component({
  selector: 'jhi-detail-contract',
  templateUrl: './detail-contract.component.html'
})
export class DetailContractComponent implements OnInit, OnDestroy {
  detailContracts: IDetailContract[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected detailContractService: DetailContractService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.detailContractService
      .query()
      .pipe(
        filter((res: HttpResponse<IDetailContract[]>) => res.ok),
        map((res: HttpResponse<IDetailContract[]>) => res.body)
      )
      .subscribe(
        (res: IDetailContract[]) => {
          this.detailContracts = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDetailContracts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDetailContract) {
    return item.id;
  }

  registerChangeInDetailContracts() {
    this.eventSubscriber = this.eventManager.subscribe('detailContractListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
