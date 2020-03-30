import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgAccount } from 'app/shared/model/pg-account.model';
import { PgAccountService } from './pg-account.service';
import { PgAccountComponent } from './pg-account.component';
import { PgAccountDetailComponent } from './pg-account-detail.component';
import { PgAccountUpdateComponent } from './pg-account-update.component';
import { PgAccountDeletePopupComponent } from './pg-account-delete-dialog.component';
import { IPgAccount } from 'app/shared/model/pg-account.model';

@Injectable({ providedIn: 'root' })
export class PgAccountResolve implements Resolve<IPgAccount> {
  constructor(private service: PgAccountService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgAccount> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgAccount>) => response.ok),
        map((pgAccount: HttpResponse<PgAccount>) => pgAccount.body)
      );
    }
    return of(new PgAccount());
  }
}

export const pgAccountRoute: Routes = [
  {
    path: '',
    component: PgAccountComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgAccountDetailComponent,
    resolve: {
      pgAccount: PgAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgAccountUpdateComponent,
    resolve: {
      pgAccount: PgAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgAccountUpdateComponent,
    resolve: {
      pgAccount: PgAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgAccount.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgAccountPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgAccountDeletePopupComponent,
    resolve: {
      pgAccount: PgAccountResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgAccount.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
