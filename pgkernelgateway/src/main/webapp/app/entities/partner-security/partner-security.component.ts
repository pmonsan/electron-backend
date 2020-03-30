import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPartnerSecurity } from 'app/shared/model/partner-security.model';
import { AccountService } from 'app/core';
import { PartnerSecurityService } from './partner-security.service';

@Component({
  selector: 'jhi-partner-security',
  templateUrl: './partner-security.component.html'
})
export class PartnerSecurityComponent implements OnInit, OnDestroy {
  partnerSecurities: IPartnerSecurity[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected partnerSecurityService: PartnerSecurityService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.partnerSecurityService
      .query()
      .pipe(
        filter((res: HttpResponse<IPartnerSecurity[]>) => res.ok),
        map((res: HttpResponse<IPartnerSecurity[]>) => res.body)
      )
      .subscribe(
        (res: IPartnerSecurity[]) => {
          this.partnerSecurities = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPartnerSecurities();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPartnerSecurity) {
    return item.id;
  }

  registerChangeInPartnerSecurities() {
    this.eventSubscriber = this.eventManager.subscribe('partnerSecurityListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
