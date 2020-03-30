import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPartnerType } from 'app/shared/model/partner-type.model';
import { AccountService } from 'app/core';
import { PartnerTypeService } from './partner-type.service';

@Component({
  selector: 'jhi-partner-type',
  templateUrl: './partner-type.component.html'
})
export class PartnerTypeComponent implements OnInit, OnDestroy {
  partnerTypes: IPartnerType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected partnerTypeService: PartnerTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.partnerTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IPartnerType[]>) => res.ok),
        map((res: HttpResponse<IPartnerType[]>) => res.body)
      )
      .subscribe(
        (res: IPartnerType[]) => {
          this.partnerTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPartnerTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPartnerType) {
    return item.id;
  }

  registerChangeInPartnerTypes() {
    this.eventSubscriber = this.eventManager.subscribe('partnerTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
