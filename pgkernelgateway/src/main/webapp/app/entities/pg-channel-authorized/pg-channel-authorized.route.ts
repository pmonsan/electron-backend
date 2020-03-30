import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgChannelAuthorized } from 'app/shared/model/pg-channel-authorized.model';
import { PgChannelAuthorizedService } from './pg-channel-authorized.service';
import { PgChannelAuthorizedComponent } from './pg-channel-authorized.component';
import { PgChannelAuthorizedDetailComponent } from './pg-channel-authorized-detail.component';
import { PgChannelAuthorizedUpdateComponent } from './pg-channel-authorized-update.component';
import { PgChannelAuthorizedDeletePopupComponent } from './pg-channel-authorized-delete-dialog.component';
import { IPgChannelAuthorized } from 'app/shared/model/pg-channel-authorized.model';

@Injectable({ providedIn: 'root' })
export class PgChannelAuthorizedResolve implements Resolve<IPgChannelAuthorized> {
  constructor(private service: PgChannelAuthorizedService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgChannelAuthorized> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgChannelAuthorized>) => response.ok),
        map((pgChannelAuthorized: HttpResponse<PgChannelAuthorized>) => pgChannelAuthorized.body)
      );
    }
    return of(new PgChannelAuthorized());
  }
}

export const pgChannelAuthorizedRoute: Routes = [
  {
    path: '',
    component: PgChannelAuthorizedComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgChannelAuthorized.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgChannelAuthorizedDetailComponent,
    resolve: {
      pgChannelAuthorized: PgChannelAuthorizedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgChannelAuthorized.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgChannelAuthorizedUpdateComponent,
    resolve: {
      pgChannelAuthorized: PgChannelAuthorizedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgChannelAuthorized.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgChannelAuthorizedUpdateComponent,
    resolve: {
      pgChannelAuthorized: PgChannelAuthorizedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgChannelAuthorized.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgChannelAuthorizedPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgChannelAuthorizedDeletePopupComponent,
    resolve: {
      pgChannelAuthorized: PgChannelAuthorizedResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgChannelAuthorized.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
