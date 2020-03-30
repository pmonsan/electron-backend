import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PriceCommission } from 'app/shared/model/price-commission.model';
import { PriceCommissionService } from './price-commission.service';
import { PriceCommissionComponent } from './price-commission.component';
import { PriceCommissionDetailComponent } from './price-commission-detail.component';
import { PriceCommissionUpdateComponent } from './price-commission-update.component';
import { PriceCommissionDeletePopupComponent } from './price-commission-delete-dialog.component';
import { IPriceCommission } from 'app/shared/model/price-commission.model';

@Injectable({ providedIn: 'root' })
export class PriceCommissionResolve implements Resolve<IPriceCommission> {
  constructor(private service: PriceCommissionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPriceCommission> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PriceCommission>) => response.ok),
        map((priceCommission: HttpResponse<PriceCommission>) => priceCommission.body)
      );
    }
    return of(new PriceCommission());
  }
}

export const priceCommissionRoute: Routes = [
  {
    path: '',
    component: PriceCommissionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.priceCommission.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PriceCommissionDetailComponent,
    resolve: {
      priceCommission: PriceCommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.priceCommission.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PriceCommissionUpdateComponent,
    resolve: {
      priceCommission: PriceCommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.priceCommission.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PriceCommissionUpdateComponent,
    resolve: {
      priceCommission: PriceCommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.priceCommission.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const priceCommissionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PriceCommissionDeletePopupComponent,
    resolve: {
      priceCommission: PriceCommissionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.priceCommission.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
