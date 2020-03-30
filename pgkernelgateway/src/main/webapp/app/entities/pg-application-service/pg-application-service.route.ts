import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgApplicationService } from 'app/shared/model/pg-application-service.model';
import { PgApplicationServiceService } from './pg-application-service.service';
import { PgApplicationServiceComponent } from './pg-application-service.component';
import { PgApplicationServiceDetailComponent } from './pg-application-service-detail.component';
import { PgApplicationServiceUpdateComponent } from './pg-application-service-update.component';
import { PgApplicationServiceDeletePopupComponent } from './pg-application-service-delete-dialog.component';
import { IPgApplicationService } from 'app/shared/model/pg-application-service.model';

@Injectable({ providedIn: 'root' })
export class PgApplicationServiceResolve implements Resolve<IPgApplicationService> {
  constructor(private service: PgApplicationServiceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgApplicationService> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgApplicationService>) => response.ok),
        map((pgApplicationService: HttpResponse<PgApplicationService>) => pgApplicationService.body)
      );
    }
    return of(new PgApplicationService());
  }
}

export const pgApplicationServiceRoute: Routes = [
  {
    path: '',
    component: PgApplicationServiceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgApplicationService.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgApplicationServiceDetailComponent,
    resolve: {
      pgApplicationService: PgApplicationServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgApplicationService.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgApplicationServiceUpdateComponent,
    resolve: {
      pgApplicationService: PgApplicationServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgApplicationService.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgApplicationServiceUpdateComponent,
    resolve: {
      pgApplicationService: PgApplicationServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgApplicationService.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgApplicationServicePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgApplicationServiceDeletePopupComponent,
    resolve: {
      pgApplicationService: PgApplicationServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgApplicationService.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
