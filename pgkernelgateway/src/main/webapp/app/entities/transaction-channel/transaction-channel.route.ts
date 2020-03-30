import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TransactionChannel } from 'app/shared/model/transaction-channel.model';
import { TransactionChannelService } from './transaction-channel.service';
import { TransactionChannelComponent } from './transaction-channel.component';
import { TransactionChannelDetailComponent } from './transaction-channel-detail.component';
import { TransactionChannelUpdateComponent } from './transaction-channel-update.component';
import { TransactionChannelDeletePopupComponent } from './transaction-channel-delete-dialog.component';
import { ITransactionChannel } from 'app/shared/model/transaction-channel.model';

@Injectable({ providedIn: 'root' })
export class TransactionChannelResolve implements Resolve<ITransactionChannel> {
  constructor(private service: TransactionChannelService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITransactionChannel> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TransactionChannel>) => response.ok),
        map((transactionChannel: HttpResponse<TransactionChannel>) => transactionChannel.body)
      );
    }
    return of(new TransactionChannel());
  }
}

export const transactionChannelRoute: Routes = [
  {
    path: '',
    component: TransactionChannelComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TransactionChannelDetailComponent,
    resolve: {
      transactionChannel: TransactionChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TransactionChannelUpdateComponent,
    resolve: {
      transactionChannel: TransactionChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TransactionChannelUpdateComponent,
    resolve: {
      transactionChannel: TransactionChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const transactionChannelPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TransactionChannelDeletePopupComponent,
    resolve: {
      transactionChannel: TransactionChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.transactionChannel.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
