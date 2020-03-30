import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPersonGender } from 'app/shared/model/person-gender.model';
import { AccountService } from 'app/core';
import { PersonGenderService } from './person-gender.service';

@Component({
  selector: 'jhi-person-gender',
  templateUrl: './person-gender.component.html'
})
export class PersonGenderComponent implements OnInit, OnDestroy {
  personGenders: IPersonGender[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected personGenderService: PersonGenderService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.personGenderService
      .query()
      .pipe(
        filter((res: HttpResponse<IPersonGender[]>) => res.ok),
        map((res: HttpResponse<IPersonGender[]>) => res.body)
      )
      .subscribe(
        (res: IPersonGender[]) => {
          this.personGenders = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInPersonGenders();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IPersonGender) {
    return item.id;
  }

  registerChangeInPersonGenders() {
    this.eventSubscriber = this.eventManager.subscribe('personGenderListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
