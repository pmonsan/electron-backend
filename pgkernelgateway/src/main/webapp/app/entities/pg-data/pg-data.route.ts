import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgData } from 'app/shared/model/pg-data.model';
import { PgDataService } from './pg-data.service';
import { PgDataComponent } from './pg-data.component';
import { PgDataDetailComponent } from './pg-data-detail.component';
import { PgDataUpdateComponent } from './pg-data-update.component';
import { PgDataDeletePopupComponent } from './pg-data-delete-dialog.component';
import { IPgData } from 'app/shared/model/pg-data.model';

@Injectable({ providedIn: 'root' })
export class PgDataResolve implements Resolve<IPgData> {
  constructor(private service: PgDataService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgData> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgData>) => response.ok),
        map((pgData: HttpResponse<PgData>) => pgData.body)
      );
    }
    return of(new PgData());
  }
}

export const pgDataRoute: Routes = [
  {
    path: '',
    component: PgDataComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgDataDetailComponent,
    resolve: {
      pgData: PgDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgDataUpdateComponent,
    resolve: {
      pgData: PgDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgDataUpdateComponent,
    resolve: {
      pgData: PgDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgData.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgDataPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgDataDeletePopupComponent,
    resolve: {
      pgData: PgDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgData.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
