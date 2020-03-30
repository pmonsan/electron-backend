import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AccountStatus } from 'app/shared/model/account-status.model';
import { AccountStatusService } from './account-status.service';
import { AccountStatusComponent } from './account-status.component';
import { AccountStatusDetailComponent } from './account-status-detail.component';
import { AccountStatusUpdateComponent } from './account-status-update.component';
import { AccountStatusDeletePopupComponent } from './account-status-delete-dialog.component';
import { IAccountStatus } from 'app/shared/model/account-status.model';

@Injectable({ providedIn: 'root' })
export class AccountStatusResolve implements Resolve<IAccountStatus> {
  constructor(private service: AccountStatusService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAccountStatus> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AccountStatus>) => response.ok),
        map((accountStatus: HttpResponse<AccountStatus>) => accountStatus.body)
      );
    }
    return of(new AccountStatus());
  }
}

export const accountStatusRoute: Routes = [
  {
    path: '',
    component: AccountStatusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccountStatusDetailComponent,
    resolve: {
      accountStatus: AccountStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccountStatusUpdateComponent,
    resolve: {
      accountStatus: AccountStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccountStatusUpdateComponent,
    resolve: {
      accountStatus: AccountStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const accountStatusPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AccountStatusDeletePopupComponent,
    resolve: {
      accountStatus: AccountStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountStatus.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
