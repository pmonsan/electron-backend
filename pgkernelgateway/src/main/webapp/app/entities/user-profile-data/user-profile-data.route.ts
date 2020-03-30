import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserProfileData } from 'app/shared/model/user-profile-data.model';
import { UserProfileDataService } from './user-profile-data.service';
import { UserProfileDataComponent } from './user-profile-data.component';
import { UserProfileDataDetailComponent } from './user-profile-data-detail.component';
import { UserProfileDataUpdateComponent } from './user-profile-data-update.component';
import { UserProfileDataDeletePopupComponent } from './user-profile-data-delete-dialog.component';
import { IUserProfileData } from 'app/shared/model/user-profile-data.model';

@Injectable({ providedIn: 'root' })
export class UserProfileDataResolve implements Resolve<IUserProfileData> {
  constructor(private service: UserProfileDataService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserProfileData> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UserProfileData>) => response.ok),
        map((userProfileData: HttpResponse<UserProfileData>) => userProfileData.body)
      );
    }
    return of(new UserProfileData());
  }
}

export const userProfileDataRoute: Routes = [
  {
    path: '',
    component: UserProfileDataComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userProfileData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserProfileDataDetailComponent,
    resolve: {
      userProfileData: UserProfileDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userProfileData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserProfileDataUpdateComponent,
    resolve: {
      userProfileData: UserProfileDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userProfileData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserProfileDataUpdateComponent,
    resolve: {
      userProfileData: UserProfileDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userProfileData.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userProfileDataPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UserProfileDataDeletePopupComponent,
    resolve: {
      userProfileData: UserProfileDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userProfileData.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
