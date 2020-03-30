import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserProfile } from 'app/shared/model/user-profile.model';
import { UserProfileService } from './user-profile.service';
import { UserProfileComponent } from './user-profile.component';
import { UserProfileDetailComponent } from './user-profile-detail.component';
import { UserProfileUpdateComponent } from './user-profile-update.component';
import { UserProfileDeletePopupComponent } from './user-profile-delete-dialog.component';
import { IUserProfile } from 'app/shared/model/user-profile.model';

@Injectable({ providedIn: 'root' })
export class UserProfileResolve implements Resolve<IUserProfile> {
  constructor(private service: UserProfileService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserProfile> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UserProfile>) => response.ok),
        map((userProfile: HttpResponse<UserProfile>) => userProfile.body)
      );
    }
    return of(new UserProfile());
  }
}

export const userProfileRoute: Routes = [
  {
    path: '',
    component: UserProfileComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userProfile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserProfileDetailComponent,
    resolve: {
      userProfile: UserProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userProfile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserProfileUpdateComponent,
    resolve: {
      userProfile: UserProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userProfile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserProfileUpdateComponent,
    resolve: {
      userProfile: UserProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userProfile.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userProfilePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UserProfileDeletePopupComponent,
    resolve: {
      userProfile: UserProfileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userProfile.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
