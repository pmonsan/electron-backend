import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IContract } from 'app/shared/model/contract.model';
import { AccountService } from 'app/core';
import { ContractService } from './contract.service';

@Component({
  selector: 'jhi-contract',
  templateUrl: './contract.component.html'
})
export class ContractComponent implements OnInit, OnDestroy {
  contracts: IContract[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected contractService: ContractService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.contractService
      .query()
      .pipe(
        filter((res: HttpResponse<IContract[]>) => res.ok),
        map((res: HttpResponse<IContract[]>) => res.body)
      )
      .subscribe(
        (res: IContract[]) => {
          this.contracts = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInContracts();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContract) {
    return item.id;
  }

  registerChangeInContracts() {
    this.eventSubscriber = this.eventManager.subscribe('contractListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
