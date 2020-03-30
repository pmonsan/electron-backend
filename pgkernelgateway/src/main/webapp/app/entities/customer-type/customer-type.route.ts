import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CustomerType } from 'app/shared/model/customer-type.model';
import { CustomerTypeService } from './customer-type.service';
import { CustomerTypeComponent } from './customer-type.component';
import { CustomerTypeDetailComponent } from './customer-type-detail.component';
import { CustomerTypeUpdateComponent } from './customer-type-update.component';
import { CustomerTypeDeletePopupComponent } from './customer-type-delete-dialog.component';
import { ICustomerType } from 'app/shared/model/customer-type.model';

@Injectable({ providedIn: 'root' })
export class CustomerTypeResolve implements Resolve<ICustomerType> {
  constructor(private service: CustomerTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICustomerType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<CustomerType>) => response.ok),
        map((customerType: HttpResponse<CustomerType>) => customerType.body)
      );
    }
    return of(new CustomerType());
  }
}

export const customerTypeRoute: Routes = [
  {
    path: '',
    component: CustomerTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CustomerTypeDetailComponent,
    resolve: {
      customerType: CustomerTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CustomerTypeUpdateComponent,
    resolve: {
      customerType: CustomerTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CustomerTypeUpdateComponent,
    resolve: {
      customerType: CustomerTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const customerTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CustomerTypeDeletePopupComponent,
    resolve: {
      customerType: CustomerTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.customerType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
