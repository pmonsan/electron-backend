import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IContractOpposition } from 'app/shared/model/contract-opposition.model';
import { AccountService } from 'app/core';
import { ContractOppositionService } from './contract-opposition.service';

@Component({
  selector: 'jhi-contract-opposition',
  templateUrl: './contract-opposition.component.html'
})
export class ContractOppositionComponent implements OnInit, OnDestroy {
  contractOppositions: IContractOpposition[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected contractOppositionService: ContractOppositionService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.contractOppositionService
      .query()
      .pipe(
        filter((res: HttpResponse<IContractOpposition[]>) => res.ok),
        map((res: HttpResponse<IContractOpposition[]>) => res.body)
      )
      .subscribe(
        (res: IContractOpposition[]) => {
          this.contractOppositions = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInContractOppositions();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IContractOpposition) {
    return item.id;
  }

  registerChangeInContractOppositions() {
    this.eventSubscriber = this.eventManager.subscribe('contractOppositionListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
