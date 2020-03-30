import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IUserProfileData } from 'app/shared/model/user-profile-data.model';
import { AccountService } from 'app/core';
import { UserProfileDataService } from './user-profile-data.service';

@Component({
  selector: 'jhi-user-profile-data',
  templateUrl: './user-profile-data.component.html'
})
export class UserProfileDataComponent implements OnInit, OnDestroy {
  userProfileData: IUserProfileData[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected userProfileDataService: UserProfileDataService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.userProfileDataService
      .query()
      .pipe(
        filter((res: HttpResponse<IUserProfileData[]>) => res.ok),
        map((res: HttpResponse<IUserProfileData[]>) => res.body)
      )
      .subscribe(
        (res: IUserProfileData[]) => {
          this.userProfileData = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInUserProfileData();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IUserProfileData) {
    return item.id;
  }

  registerChangeInUserProfileData() {
    this.eventSubscriber = this.eventManager.subscribe('userProfileDataListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
