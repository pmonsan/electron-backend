import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CustomerLimit } from 'app/shared/model/customer-limit.model';
import { CustomerLimitService } from './customer-limit.service';
import { CustomerLimitComponent } from './customer-limit.component';
import { CustomerLimitDetailComponent } from './customer-limit-detail.component';
import { CustomerLimitUpdateComponent } from './customer-limit-update.component';
import { CustomerLimitDeletePopupComponent } from './customer-limit-delete-dialog.component';
import { ICustomerLimit } from 'app/shared/model/customer-limit.model';

@Injectable({ providedIn: 'root' })
export class CustomerLimitResolve implements Resolve<ICustomerLimit> {
  constructor(private service: CustomerLimitService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICustomerLimit> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CustomerLimit>) => response.ok),
        map((customerLimit: HttpResponse<CustomerLimit>) => customerLimit.body)
      );
    }
    return of(new CustomerLimit());
  }
}

export const customerLimitRoute: Routes = [
  {
    path: '',
    component: CustomerLimitComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerLimit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CustomerLimitDetailComponent,
    resolve: {
      customerLimit: CustomerLimitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerLimit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CustomerLimitUpdateComponent,
    resolve: {
      customerLimit: CustomerLimitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerLimit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CustomerLimitUpdateComponent,
    resolve: {
      customerLimit: CustomerLimitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerLimit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customerLimitPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CustomerLimitDeletePopupComponent,
    resolve: {
      customerLimit: CustomerLimitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerLimit.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
