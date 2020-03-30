import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgService } from 'app/shared/model/pg-service.model';
import { PgServiceService } from './pg-service.service';
import { PgServiceComponent } from './pg-service.component';
import { PgServiceDetailComponent } from './pg-service-detail.component';
import { PgServiceUpdateComponent } from './pg-service-update.component';
import { PgServiceDeletePopupComponent } from './pg-service-delete-dialog.component';
import { IPgService } from 'app/shared/model/pg-service.model';

@Injectable({ providedIn: 'root' })
export class PgServiceResolve implements Resolve<IPgService> {
  constructor(private service: PgServiceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgService> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgService>) => response.ok),
        map((pgService: HttpResponse<PgService>) => pgService.body)
      );
    }
    return of(new PgService());
  }
}

export const pgServiceRoute: Routes = [
  {
    path: '',
    component: PgServiceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgService.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgServiceDetailComponent,
    resolve: {
      pgService: PgServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgService.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgServiceUpdateComponent,
    resolve: {
      pgService: PgServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgService.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgServiceUpdateComponent,
    resolve: {
      pgService: PgServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgService.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgServicePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgServiceDeletePopupComponent,
    resolve: {
      pgService: PgServiceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgService.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
