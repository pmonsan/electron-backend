import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TransactionStatus } from 'app/shared/model/transaction-status.model';
import { TransactionStatusService } from './transaction-status.service';
import { TransactionStatusComponent } from './transaction-status.component';
import { TransactionStatusDetailComponent } from './transaction-status-detail.component';
import { TransactionStatusUpdateComponent } from './transaction-status-update.component';
import { TransactionStatusDeletePopupComponent } from './transaction-status-delete-dialog.component';
import { ITransactionStatus } from 'app/shared/model/transaction-status.model';

@Injectable({ providedIn: 'root' })
export class TransactionStatusResolve implements Resolve<ITransactionStatus> {
  constructor(private service: TransactionStatusService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransactionStatus> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TransactionStatus>) => response.ok),
        map((transactionStatus: HttpResponse<TransactionStatus>) => transactionStatus.body)
      );
    }
    return of(new TransactionStatus());
  }
}

export const transactionStatusRoute: Routes = [
  {
    path: '',
    component: TransactionStatusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionStatusDetailComponent,
    resolve: {
      transactionStatus: TransactionStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionStatusUpdateComponent,
    resolve: {
      transactionStatus: TransactionStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionStatusUpdateComponent,
    resolve: {
      transactionStatus: TransactionStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionStatusPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransactionStatusDeletePopupComponent,
    resolve: {
      transactionStatus: TransactionStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionStatus.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
