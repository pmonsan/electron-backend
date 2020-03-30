import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { SubscriptionPrice } from 'app/shared/model/subscription-price.model';
import { SubscriptionPriceService } from './subscription-price.service';
import { SubscriptionPriceComponent } from './subscription-price.component';
import { SubscriptionPriceDetailComponent } from './subscription-price-detail.component';
import { SubscriptionPriceUpdateComponent } from './subscription-price-update.component';
import { SubscriptionPriceDeletePopupComponent } from './subscription-price-delete-dialog.component';
import { ISubscriptionPrice } from 'app/shared/model/subscription-price.model';

@Injectable({ providedIn: 'root' })
export class SubscriptionPriceResolve implements Resolve<ISubscriptionPrice> {
  constructor(private service: SubscriptionPriceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISubscriptionPrice> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<SubscriptionPrice>) => response.ok),
        map((subscriptionPrice: HttpResponse<SubscriptionPrice>) => subscriptionPrice.body)
      );
    }
    return of(new SubscriptionPrice());
  }
}

export const subscriptionPriceRoute: Routes = [
  {
    path: '',
    component: SubscriptionPriceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.subscriptionPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SubscriptionPriceDetailComponent,
    resolve: {
      subscriptionPrice: SubscriptionPriceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.subscriptionPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SubscriptionPriceUpdateComponent,
    resolve: {
      subscriptionPrice: SubscriptionPriceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.subscriptionPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SubscriptionPriceUpdateComponent,
    resolve: {
      subscriptionPrice: SubscriptionPriceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.subscriptionPrice.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const subscriptionPricePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SubscriptionPriceDeletePopupComponent,
    resolve: {
      subscriptionPrice: SubscriptionPriceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.subscriptionPrice.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
