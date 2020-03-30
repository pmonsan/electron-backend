import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CustomerBlacklist } from 'app/shared/model/customer-blacklist.model';
import { CustomerBlacklistService } from './customer-blacklist.service';
import { CustomerBlacklistComponent } from './customer-blacklist.component';
import { CustomerBlacklistDetailComponent } from './customer-blacklist-detail.component';
import { CustomerBlacklistUpdateComponent } from './customer-blacklist-update.component';
import { CustomerBlacklistDeletePopupComponent } from './customer-blacklist-delete-dialog.component';
import { ICustomerBlacklist } from 'app/shared/model/customer-blacklist.model';

@Injectable({ providedIn: 'root' })
export class CustomerBlacklistResolve implements Resolve<ICustomerBlacklist> {
  constructor(private service: CustomerBlacklistService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICustomerBlacklist> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CustomerBlacklist>) => response.ok),
        map((customerBlacklist: HttpResponse<CustomerBlacklist>) => customerBlacklist.body)
      );
    }
    return of(new CustomerBlacklist());
  }
}

export const customerBlacklistRoute: Routes = [
  {
    path: '',
    component: CustomerBlacklistComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerBlacklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CustomerBlacklistDetailComponent,
    resolve: {
      customerBlacklist: CustomerBlacklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerBlacklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CustomerBlacklistUpdateComponent,
    resolve: {
      customerBlacklist: CustomerBlacklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerBlacklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CustomerBlacklistUpdateComponent,
    resolve: {
      customerBlacklist: CustomerBlacklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerBlacklist.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customerBlacklistPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CustomerBlacklistDeletePopupComponent,
    resolve: {
      customerBlacklist: CustomerBlacklistResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerBlacklist.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
