import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBeneficiaryType } from 'app/shared/model/beneficiary-type.model';
import { AccountService } from 'app/core';
import { BeneficiaryTypeService } from './beneficiary-type.service';

@Component({
  selector: 'jhi-beneficiary-type',
  templateUrl: './beneficiary-type.component.html'
})
export class BeneficiaryTypeComponent implements OnInit, OnDestroy {
  beneficiaryTypes: IBeneficiaryType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected beneficiaryTypeService: BeneficiaryTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.beneficiaryTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IBeneficiaryType[]>) => res.ok),
        map((res: HttpResponse<IBeneficiaryType[]>) => res.body)
      )
      .subscribe(
        (res: IBeneficiaryType[]) => {
          this.beneficiaryTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBeneficiaryTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBeneficiaryType) {
    return item.id;
  }

  registerChangeInBeneficiaryTypes() {
    this.eventSubscriber = this.eventManager.subscribe('beneficiaryTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
