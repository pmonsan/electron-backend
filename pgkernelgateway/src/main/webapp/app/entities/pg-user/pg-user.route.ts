import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgUser } from 'app/shared/model/pg-user.model';
import { PgUserService } from './pg-user.service';
import { PgUserComponent } from './pg-user.component';
import { PgUserDetailComponent } from './pg-user-detail.component';
import { PgUserUpdateComponent } from './pg-user-update.component';
import { PgUserDeletePopupComponent } from './pg-user-delete-dialog.component';
import { IPgUser } from 'app/shared/model/pg-user.model';

@Injectable({ providedIn: 'root' })
export class PgUserResolve implements Resolve<IPgUser> {
  constructor(private service: PgUserService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgUser> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgUser>) => response.ok),
        map((pgUser: HttpResponse<PgUser>) => pgUser.body)
      );
    }
    return of(new PgUser());
  }
}

export const pgUserRoute: Routes = [
  {
    path: '',
    component: PgUserComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgUserDetailComponent,
    resolve: {
      pgUser: PgUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgUserUpdateComponent,
    resolve: {
      pgUser: PgUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgUserUpdateComponent,
    resolve: {
      pgUser: PgUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgUserPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgUserDeletePopupComponent,
    resolve: {
      pgUser: PgUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgUser.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
