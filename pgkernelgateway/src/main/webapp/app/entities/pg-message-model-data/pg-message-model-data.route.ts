import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgMessageModelData } from 'app/shared/model/pg-message-model-data.model';
import { PgMessageModelDataService } from './pg-message-model-data.service';
import { PgMessageModelDataComponent } from './pg-message-model-data.component';
import { PgMessageModelDataDetailComponent } from './pg-message-model-data-detail.component';
import { PgMessageModelDataUpdateComponent } from './pg-message-model-data-update.component';
import { PgMessageModelDataDeletePopupComponent } from './pg-message-model-data-delete-dialog.component';
import { IPgMessageModelData } from 'app/shared/model/pg-message-model-data.model';

@Injectable({ providedIn: 'root' })
export class PgMessageModelDataResolve implements Resolve<IPgMessageModelData> {
  constructor(private service: PgMessageModelDataService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgMessageModelData> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgMessageModelData>) => response.ok),
        map((pgMessageModelData: HttpResponse<PgMessageModelData>) => pgMessageModelData.body)
      );
    }
    return of(new PgMessageModelData());
  }
}

export const pgMessageModelDataRoute: Routes = [
  {
    path: '',
    component: PgMessageModelDataComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageModelData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgMessageModelDataDetailComponent,
    resolve: {
      pgMessageModelData: PgMessageModelDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageModelData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgMessageModelDataUpdateComponent,
    resolve: {
      pgMessageModelData: PgMessageModelDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageModelData.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgMessageModelDataUpdateComponent,
    resolve: {
      pgMessageModelData: PgMessageModelDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageModelData.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgMessageModelDataPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgMessageModelDataDeletePopupComponent,
    resolve: {
      pgMessageModelData: PgMessageModelDataResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageModelData.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
