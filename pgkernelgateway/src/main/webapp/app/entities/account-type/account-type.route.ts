import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AccountType } from 'app/shared/model/account-type.model';
import { AccountTypeService } from './account-type.service';
import { AccountTypeComponent } from './account-type.component';
import { AccountTypeDetailComponent } from './account-type-detail.component';
import { AccountTypeUpdateComponent } from './account-type-update.component';
import { AccountTypeDeletePopupComponent } from './account-type-delete-dialog.component';
import { IAccountType } from 'app/shared/model/account-type.model';

@Injectable({ providedIn: 'root' })
export class AccountTypeResolve implements Resolve<IAccountType> {
  constructor(private service: AccountTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAccountType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AccountType>) => response.ok),
        map((accountType: HttpResponse<AccountType>) => accountType.body)
      );
    }
    return of(new AccountType());
  }
}

export const accountTypeRoute: Routes = [
  {
    path: '',
    component: AccountTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AccountTypeDetailComponent,
    resolve: {
      accountType: AccountTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AccountTypeUpdateComponent,
    resolve: {
      accountType: AccountTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AccountTypeUpdateComponent,
    resolve: {
      accountType: AccountTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const accountTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AccountTypeDeletePopupComponent,
    resolve: {
      accountType: AccountTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.accountType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
