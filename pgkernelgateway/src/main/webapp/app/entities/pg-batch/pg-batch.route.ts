import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgBatch } from 'app/shared/model/pg-batch.model';
import { PgBatchService } from './pg-batch.service';
import { PgBatchComponent } from './pg-batch.component';
import { PgBatchDetailComponent } from './pg-batch-detail.component';
import { PgBatchUpdateComponent } from './pg-batch-update.component';
import { PgBatchDeletePopupComponent } from './pg-batch-delete-dialog.component';
import { IPgBatch } from 'app/shared/model/pg-batch.model';

@Injectable({ providedIn: 'root' })
export class PgBatchResolve implements Resolve<IPgBatch> {
  constructor(private service: PgBatchService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgBatch> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgBatch>) => response.ok),
        map((pgBatch: HttpResponse<PgBatch>) => pgBatch.body)
      );
    }
    return of(new PgBatch());
  }
}

export const pgBatchRoute: Routes = [
  {
    path: '',
    component: PgBatchComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgBatch.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgBatchDetailComponent,
    resolve: {
      pgBatch: PgBatchResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgBatch.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgBatchUpdateComponent,
    resolve: {
      pgBatch: PgBatchResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgBatch.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgBatchUpdateComponent,
    resolve: {
      pgBatch: PgBatchResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgBatch.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgBatchPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgBatchDeletePopupComponent,
    resolve: {
      pgBatch: PgBatchResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgBatch.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
