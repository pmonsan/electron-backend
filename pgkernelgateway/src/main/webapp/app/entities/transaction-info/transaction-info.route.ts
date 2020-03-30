import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TransactionInfo } from 'app/shared/model/transaction-info.model';
import { TransactionInfoService } from './transaction-info.service';
import { TransactionInfoComponent } from './transaction-info.component';
import { TransactionInfoDetailComponent } from './transaction-info-detail.component';
import { TransactionInfoUpdateComponent } from './transaction-info-update.component';
import { TransactionInfoDeletePopupComponent } from './transaction-info-delete-dialog.component';
import { ITransactionInfo } from 'app/shared/model/transaction-info.model';

@Injectable({ providedIn: 'root' })
export class TransactionInfoResolve implements Resolve<ITransactionInfo> {
  constructor(private service: TransactionInfoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransactionInfo> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TransactionInfo>) => response.ok),
        map((transactionInfo: HttpResponse<TransactionInfo>) => transactionInfo.body)
      );
    }
    return of(new TransactionInfo());
  }
}

export const transactionInfoRoute: Routes = [
  {
    path: '',
    component: TransactionInfoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionInfoDetailComponent,
    resolve: {
      transactionInfo: TransactionInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionInfoUpdateComponent,
    resolve: {
      transactionInfo: TransactionInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionInfoUpdateComponent,
    resolve: {
      transactionInfo: TransactionInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionInfo.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionInfoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransactionInfoDeletePopupComponent,
    resolve: {
      transactionInfo: TransactionInfoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionInfo.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
