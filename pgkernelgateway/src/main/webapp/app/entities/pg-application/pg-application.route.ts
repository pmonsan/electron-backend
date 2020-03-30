import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgApplication } from 'app/shared/model/pg-application.model';
import { PgApplicationService } from './pg-application.service';
import { PgApplicationComponent } from './pg-application.component';
import { PgApplicationDetailComponent } from './pg-application-detail.component';
import { PgApplicationUpdateComponent } from './pg-application-update.component';
import { PgApplicationDeletePopupComponent } from './pg-application-delete-dialog.component';
import { IPgApplication } from 'app/shared/model/pg-application.model';

@Injectable({ providedIn: 'root' })
export class PgApplicationResolve implements Resolve<IPgApplication> {
  constructor(private service: PgApplicationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgApplication> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgApplication>) => response.ok),
        map((pgApplication: HttpResponse<PgApplication>) => pgApplication.body)
      );
    }
    return of(new PgApplication());
  }
}

export const pgApplicationRoute: Routes = [
  {
    path: '',
    component: PgApplicationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgApplication.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgApplicationDetailComponent,
    resolve: {
      pgApplication: PgApplicationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgApplication.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgApplicationUpdateComponent,
    resolve: {
      pgApplication: PgApplicationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgApplication.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgApplicationUpdateComponent,
    resolve: {
      pgApplication: PgApplicationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgApplication.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgApplicationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgApplicationDeletePopupComponent,
    resolve: {
      pgApplication: PgApplicationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgApplication.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
