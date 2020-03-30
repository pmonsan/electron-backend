import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgTransactionType2 } from 'app/shared/model/pg-transaction-type-2.model';
import { PgTransactionType2Service } from './pg-transaction-type-2.service';
import { PgTransactionType2Component } from './pg-transaction-type-2.component';
import { PgTransactionType2DetailComponent } from './pg-transaction-type-2-detail.component';
import { PgTransactionType2UpdateComponent } from './pg-transaction-type-2-update.component';
import { PgTransactionType2DeletePopupComponent } from './pg-transaction-type-2-delete-dialog.component';
import { IPgTransactionType2 } from 'app/shared/model/pg-transaction-type-2.model';

@Injectable({ providedIn: 'root' })
export class PgTransactionType2Resolve implements Resolve<IPgTransactionType2> {
  constructor(private service: PgTransactionType2Service) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgTransactionType2> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgTransactionType2>) => response.ok),
        map((pgTransactionType2: HttpResponse<PgTransactionType2>) => pgTransactionType2.body)
      );
    }
    return of(new PgTransactionType2());
  }
}

export const pgTransactionType2Route: Routes = [
  {
    path: '',
    component: PgTransactionType2Component,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgTransactionType2.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgTransactionType2DetailComponent,
    resolve: {
      pgTransactionType2: PgTransactionType2Resolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgTransactionType2.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgTransactionType2UpdateComponent,
    resolve: {
      pgTransactionType2: PgTransactionType2Resolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgTransactionType2.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgTransactionType2UpdateComponent,
    resolve: {
      pgTransactionType2: PgTransactionType2Resolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgTransactionType2.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgTransactionType2PopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgTransactionType2DeletePopupComponent,
    resolve: {
      pgTransactionType2: PgTransactionType2Resolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgTransactionType2.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
