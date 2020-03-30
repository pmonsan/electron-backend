import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TransactionCommission } from 'app/shared/model/transaction-commission.model';
import { TransactionCommissionService } from './transaction-commission.service';
import { TransactionCommissionComponent } from './transaction-commission.component';
import { TransactionCommissionDetailComponent } from './transaction-commission-detail.component';
import { TransactionCommissionUpdateComponent } from './transaction-commission-update.component';
import { TransactionCommissionDeletePopupComponent } from './transaction-commission-delete-dialog.component';
import { ITransactionCommission } from 'app/shared/model/transaction-commission.model';

@Injectable({ providedIn: 'root' })
export class TransactionCommissionResolve implements Resolve<ITransactionCommission> {
  constructor(private service: TransactionCommissionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransactionCommission> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TransactionCommission>) => response.ok),
        map((transactionCommission: HttpResponse<TransactionCommission>) => transactionCommission.body)
      );
    }
    return of(new TransactionCommission());
  }
}

export const transactionCommissionRoute: Routes = [
  {
    path: '',
    component: TransactionCommissionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionCommission.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionCommissionDetailComponent,
    resolve: {
      transactionCommission: TransactionCommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionCommission.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionCommissionUpdateComponent,
    resolve: {
      transactionCommission: TransactionCommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionCommission.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionCommissionUpdateComponent,
    resolve: {
      transactionCommission: TransactionCommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionCommission.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionCommissionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransactionCommissionDeletePopupComponent,
    resolve: {
      transactionCommission: TransactionCommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionCommission.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
