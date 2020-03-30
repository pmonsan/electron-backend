import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PricePlan } from 'app/shared/model/price-plan.model';
import { PricePlanService } from './price-plan.service';
import { PricePlanComponent } from './price-plan.component';
import { PricePlanDetailComponent } from './price-plan-detail.component';
import { PricePlanUpdateComponent } from './price-plan-update.component';
import { PricePlanDeletePopupComponent } from './price-plan-delete-dialog.component';
import { IPricePlan } from 'app/shared/model/price-plan.model';

@Injectable({ providedIn: 'root' })
export class PricePlanResolve implements Resolve<IPricePlan> {
  constructor(private service: PricePlanService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPricePlan> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PricePlan>) => response.ok),
        map((pricePlan: HttpResponse<PricePlan>) => pricePlan.body)
      );
    }
    return of(new PricePlan());
  }
}

export const pricePlanRoute: Routes = [
  {
    path: '',
    component: PricePlanComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pricePlan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PricePlanDetailComponent,
    resolve: {
      pricePlan: PricePlanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pricePlan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PricePlanUpdateComponent,
    resolve: {
      pricePlan: PricePlanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pricePlan.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PricePlanUpdateComponent,
    resolve: {
      pricePlan: PricePlanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pricePlan.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pricePlanPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PricePlanDeletePopupComponent,
    resolve: {
      pricePlan: PricePlanResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pricePlan.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
