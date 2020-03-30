import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TransactionType } from 'app/shared/model/transaction-type.model';
import { TransactionTypeService } from './transaction-type.service';
import { TransactionTypeComponent } from './transaction-type.component';
import { TransactionTypeDetailComponent } from './transaction-type-detail.component';
import { TransactionTypeUpdateComponent } from './transaction-type-update.component';
import { TransactionTypeDeletePopupComponent } from './transaction-type-delete-dialog.component';
import { ITransactionType } from 'app/shared/model/transaction-type.model';

@Injectable({ providedIn: 'root' })
export class TransactionTypeResolve implements Resolve<ITransactionType> {
  constructor(private service: TransactionTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransactionType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TransactionType>) => response.ok),
        map((transactionType: HttpResponse<TransactionType>) => transactionType.body)
      );
    }
    return of(new TransactionType());
  }
}

export const transactionTypeRoute: Routes = [
  {
    path: '',
    component: TransactionTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionTypeDetailComponent,
    resolve: {
      transactionType: TransactionTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionTypeUpdateComponent,
    resolve: {
      transactionType: TransactionTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionTypeUpdateComponent,
    resolve: {
      transactionType: TransactionTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransactionTypeDeletePopupComponent,
    resolve: {
      transactionType: TransactionTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
