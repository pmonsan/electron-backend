import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPersonType } from 'app/shared/model/person-type.model';
import { AccountService } from 'app/core';
import { PersonTypeService } from './person-type.service';

@Component({
  selector: 'jhi-person-type',
  templateUrl: './person-type.component.html'
})
export class PersonTypeComponent implements OnInit, OnDestroy {
  personTypes: IPersonType[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected personTypeService: PersonTypeService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.personTypeService
      .query()
      .pipe(
        filter((res: HttpResponse<IPersonType[]>) => res.ok),
        map((res: HttpResponse<IPersonType[]>) => res.body)
      )
      .subscribe(
        (res: IPersonType[]) => {
          this.personTypes = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPersonTypes();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPersonType) {
    return item.id;
  }

  registerChangeInPersonTypes() {
    this.eventSubscriber = this.eventManager.subscribe('personTypeListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
