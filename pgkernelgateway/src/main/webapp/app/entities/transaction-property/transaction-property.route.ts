import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TransactionProperty } from 'app/shared/model/transaction-property.model';
import { TransactionPropertyService } from './transaction-property.service';
import { TransactionPropertyComponent } from './transaction-property.component';
import { TransactionPropertyDetailComponent } from './transaction-property-detail.component';
import { TransactionPropertyUpdateComponent } from './transaction-property-update.component';
import { TransactionPropertyDeletePopupComponent } from './transaction-property-delete-dialog.component';
import { ITransactionProperty } from 'app/shared/model/transaction-property.model';

@Injectable({ providedIn: 'root' })
export class TransactionPropertyResolve implements Resolve<ITransactionProperty> {
  constructor(private service: TransactionPropertyService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransactionProperty> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TransactionProperty>) => response.ok),
        map((transactionProperty: HttpResponse<TransactionProperty>) => transactionProperty.body)
      );
    }
    return of(new TransactionProperty());
  }
}

export const transactionPropertyRoute: Routes = [
  {
    path: '',
    component: TransactionPropertyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionProperty.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionPropertyDetailComponent,
    resolve: {
      transactionProperty: TransactionPropertyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionProperty.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionPropertyUpdateComponent,
    resolve: {
      transactionProperty: TransactionPropertyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionProperty.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionPropertyUpdateComponent,
    resolve: {
      transactionProperty: TransactionPropertyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionProperty.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionPropertyPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransactionPropertyDeletePopupComponent,
    resolve: {
      transactionProperty: TransactionPropertyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionProperty.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
