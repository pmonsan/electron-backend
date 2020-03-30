import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBeneficiaryRelationship } from 'app/shared/model/beneficiary-relationship.model';
import { AccountService } from 'app/core';
import { BeneficiaryRelationshipService } from './beneficiary-relationship.service';

@Component({
  selector: 'jhi-beneficiary-relationship',
  templateUrl: './beneficiary-relationship.component.html'
})
export class BeneficiaryRelationshipComponent implements OnInit, OnDestroy {
  beneficiaryRelationships: IBeneficiaryRelationship[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected beneficiaryRelationshipService: BeneficiaryRelationshipService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.beneficiaryRelationshipService
      .query()
      .pipe(
        filter((res: HttpResponse<IBeneficiaryRelationship[]>) => res.ok),
        map((res: HttpResponse<IBeneficiaryRelationship[]>) => res.body)
      )
      .subscribe(
        (res: IBeneficiaryRelationship[]) => {
          this.beneficiaryRelationships = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBeneficiaryRelationships();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBeneficiaryRelationship) {
    return item.id;
  }

  registerChangeInBeneficiaryRelationships() {
    this.eventSubscriber = this.eventManager.subscribe('beneficiaryRelationshipListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
