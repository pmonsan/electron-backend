import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgTransactionType1 } from 'app/shared/model/pg-transaction-type-1.model';
import { PgTransactionType1Service } from './pg-transaction-type-1.service';
import { PgTransactionType1Component } from './pg-transaction-type-1.component';
import { PgTransactionType1DetailComponent } from './pg-transaction-type-1-detail.component';
import { PgTransactionType1UpdateComponent } from './pg-transaction-type-1-update.component';
import { PgTransactionType1DeletePopupComponent } from './pg-transaction-type-1-delete-dialog.component';
import { IPgTransactionType1 } from 'app/shared/model/pg-transaction-type-1.model';

@Injectable({ providedIn: 'root' })
export class PgTransactionType1Resolve implements Resolve<IPgTransactionType1> {
  constructor(private service: PgTransactionType1Service) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgTransactionType1> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgTransactionType1>) => response.ok),
        map((pgTransactionType1: HttpResponse<PgTransactionType1>) => pgTransactionType1.body)
      );
    }
    return of(new PgTransactionType1());
  }
}

export const pgTransactionType1Route: Routes = [
  {
    path: '',
    component: PgTransactionType1Component,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgTransactionType1.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgTransactionType1DetailComponent,
    resolve: {
      pgTransactionType1: PgTransactionType1Resolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgTransactionType1.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgTransactionType1UpdateComponent,
    resolve: {
      pgTransactionType1: PgTransactionType1Resolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgTransactionType1.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgTransactionType1UpdateComponent,
    resolve: {
      pgTransactionType1: PgTransactionType1Resolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgTransactionType1.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgTransactionType1PopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgTransactionType1DeletePopupComponent,
    resolve: {
      pgTransactionType1: PgTransactionType1Resolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgTransactionType1.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
