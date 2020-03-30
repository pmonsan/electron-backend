import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CustomerSubscription } from 'app/shared/model/customer-subscription.model';
import { CustomerSubscriptionService } from './customer-subscription.service';
import { CustomerSubscriptionComponent } from './customer-subscription.component';
import { CustomerSubscriptionDetailComponent } from './customer-subscription-detail.component';
import { CustomerSubscriptionUpdateComponent } from './customer-subscription-update.component';
import { CustomerSubscriptionDeletePopupComponent } from './customer-subscription-delete-dialog.component';
import { ICustomerSubscription } from 'app/shared/model/customer-subscription.model';

@Injectable({ providedIn: 'root' })
export class CustomerSubscriptionResolve implements Resolve<ICustomerSubscription> {
  constructor(private service: CustomerSubscriptionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICustomerSubscription> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CustomerSubscription>) => response.ok),
        map((customerSubscription: HttpResponse<CustomerSubscription>) => customerSubscription.body)
      );
    }
    return of(new CustomerSubscription());
  }
}

export const customerSubscriptionRoute: Routes = [
  {
    path: '',
    component: CustomerSubscriptionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerSubscription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CustomerSubscriptionDetailComponent,
    resolve: {
      customerSubscription: CustomerSubscriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerSubscription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CustomerSubscriptionUpdateComponent,
    resolve: {
      customerSubscription: CustomerSubscriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerSubscription.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CustomerSubscriptionUpdateComponent,
    resolve: {
      customerSubscription: CustomerSubscriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerSubscription.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customerSubscriptionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CustomerSubscriptionDeletePopupComponent,
    resolve: {
      customerSubscription: CustomerSubscriptionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerSubscription.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
