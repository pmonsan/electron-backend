import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AccountFeature } from 'app/shared/model/account-feature.model';
import { AccountFeatureService } from './account-feature.service';
import { AccountFeatureComponent } from './account-feature.component';
import { AccountFeatureDetailComponent } from './account-feature-detail.component';
import { AccountFeatureUpdateComponent } from './account-feature-update.component';
import { AccountFeatureDeletePopupComponent } from './account-feature-delete-dialog.component';
import { IAccountFeature } from 'app/shared/model/account-feature.model';

@Injectable({ providedIn: 'root' })
export class AccountFeatureResolve implements Resolve<IAccountFeature> {
  constructor(private service: AccountFeatureService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAccountFeature> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AccountFeature>) => response.ok),
        map((accountFeature: HttpResponse<AccountFeature>) => accountFeature.body)
      );
    }
    return of(new AccountFeature());
  }
}

export const accountFeatureRoute: Routes = [
  {
    path: '',
    component: AccountFeatureComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountFeature.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccountFeatureDetailComponent,
    resolve: {
      accountFeature: AccountFeatureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountFeature.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccountFeatureUpdateComponent,
    resolve: {
      accountFeature: AccountFeatureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountFeature.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccountFeatureUpdateComponent,
    resolve: {
      accountFeature: AccountFeatureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountFeature.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const accountFeaturePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AccountFeatureDeletePopupComponent,
    resolve: {
      accountFeature: AccountFeatureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountFeature.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
