import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICustomerType } from 'app/shared/model/customer-type.model';
import { AccountService } from 'app/core';
import { CustomerTypeService } from './customer-type.service';

@Component({
  selector: 'jhi-customer-type',
  templateUrl: './customer-type.component.html'
})
export class CustomerTypeComponent implements OnInit, OnDestroy {
  customerTypes: ICustomerType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected customerTypeService: CustomerTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.customerTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<ICustomerType[]>) => res.ok),
        map((res: HttpResponse<ICustomerType[]>) => res.body)
      )
      .subscribe(
        (res: ICustomerType[]) => {
          this.customerTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInCustomerTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ICustomerType) {
    return item.id;
  }

  registerChangeInCustomerTypes() {
    this.eventSubscriber = this.eventManager.subscribe('customerTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
