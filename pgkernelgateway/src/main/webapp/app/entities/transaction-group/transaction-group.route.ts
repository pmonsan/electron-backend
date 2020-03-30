import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TransactionGroup } from 'app/shared/model/transaction-group.model';
import { TransactionGroupService } from './transaction-group.service';
import { TransactionGroupComponent } from './transaction-group.component';
import { TransactionGroupDetailComponent } from './transaction-group-detail.component';
import { TransactionGroupUpdateComponent } from './transaction-group-update.component';
import { TransactionGroupDeletePopupComponent } from './transaction-group-delete-dialog.component';
import { ITransactionGroup } from 'app/shared/model/transaction-group.model';

@Injectable({ providedIn: 'root' })
export class TransactionGroupResolve implements Resolve<ITransactionGroup> {
  constructor(private service: TransactionGroupService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransactionGroup> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TransactionGroup>) => response.ok),
        map((transactionGroup: HttpResponse<TransactionGroup>) => transactionGroup.body)
      );
    }
    return of(new TransactionGroup());
  }
}

export const transactionGroupRoute: Routes = [
  {
    path: '',
    component: TransactionGroupComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionGroupDetailComponent,
    resolve: {
      transactionGroup: TransactionGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionGroupUpdateComponent,
    resolve: {
      transactionGroup: TransactionGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionGroupUpdateComponent,
    resolve: {
      transactionGroup: TransactionGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionGroup.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionGroupPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransactionGroupDeletePopupComponent,
    resolve: {
      transactionGroup: TransactionGroupResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionGroup.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
