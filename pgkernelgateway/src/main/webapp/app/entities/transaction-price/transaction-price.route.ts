import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TransactionPrice } from 'app/shared/model/transaction-price.model';
import { TransactionPriceService } from './transaction-price.service';
import { TransactionPriceComponent } from './transaction-price.component';
import { TransactionPriceDetailComponent } from './transaction-price-detail.component';
import { TransactionPriceUpdateComponent } from './transaction-price-update.component';
import { TransactionPriceDeletePopupComponent } from './transaction-price-delete-dialog.component';
import { ITransactionPrice } from 'app/shared/model/transaction-price.model';

@Injectable({ providedIn: 'root' })
export class TransactionPriceResolve implements Resolve<ITransactionPrice> {
  constructor(private service: TransactionPriceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransactionPrice> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TransactionPrice>) => response.ok),
        map((transactionPrice: HttpResponse<TransactionPrice>) => transactionPrice.body)
      );
    }
    return of(new TransactionPrice());
  }
}

export const transactionPriceRoute: Routes = [
  {
    path: '',
    component: TransactionPriceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionPriceDetailComponent,
    resolve: {
      transactionPrice: TransactionPriceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionPriceUpdateComponent,
    resolve: {
      transactionPrice: TransactionPriceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionPriceUpdateComponent,
    resolve: {
      transactionPrice: TransactionPriceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionPricePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransactionPriceDeletePopupComponent,
    resolve: {
      transactionPrice: TransactionPriceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionPrice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
