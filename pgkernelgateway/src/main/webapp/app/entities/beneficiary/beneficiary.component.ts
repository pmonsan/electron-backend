import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBeneficiary } from 'app/shared/model/beneficiary.model';
import { AccountService } from 'app/core';
import { BeneficiaryService } from './beneficiary.service';

@Component({
  selector: 'jhi-beneficiary',
  templateUrl: './beneficiary.component.html'
})
export class BeneficiaryComponent implements OnInit, OnDestroy {
  beneficiaries: IBeneficiary[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected beneficiaryService: BeneficiaryService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.beneficiaryService
      .query()
      .pipe(
        filter((res: HttpResponse<IBeneficiary[]>) => res.ok),
        map((res: HttpResponse<IBeneficiary[]>) => res.body)
      )
      .subscribe(
        (res: IBeneficiary[]) => {
          this.beneficiaries = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBeneficiaries();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBeneficiary) {
    return item.id;
  }

  registerChangeInBeneficiaries() {
    this.eventSubscriber = this.eventManager.subscribe('beneficiaryListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
