import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DetailTransaction } from 'app/shared/model/detail-transaction.model';
import { DetailTransactionService } from './detail-transaction.service';
import { DetailTransactionComponent } from './detail-transaction.component';
import { DetailTransactionDetailComponent } from './detail-transaction-detail.component';
import { DetailTransactionUpdateComponent } from './detail-transaction-update.component';
import { DetailTransactionDeletePopupComponent } from './detail-transaction-delete-dialog.component';
import { IDetailTransaction } from 'app/shared/model/detail-transaction.model';

@Injectable({ providedIn: 'root' })
export class DetailTransactionResolve implements Resolve<IDetailTransaction> {
  constructor(private service: DetailTransactionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDetailTransaction> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DetailTransaction>) => response.ok),
        map((detailTransaction: HttpResponse<DetailTransaction>) => detailTransaction.body)
      );
    }
    return of(new DetailTransaction());
  }
}

export const detailTransactionRoute: Routes = [
  {
    path: '',
    component: DetailTransactionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.detailTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DetailTransactionDetailComponent,
    resolve: {
      detailTransaction: DetailTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.detailTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DetailTransactionUpdateComponent,
    resolve: {
      detailTransaction: DetailTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.detailTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DetailTransactionUpdateComponent,
    resolve: {
      detailTransaction: DetailTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.detailTransaction.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const detailTransactionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DetailTransactionDeletePopupComponent,
    resolve: {
      detailTransaction: DetailTransactionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.detailTransaction.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
