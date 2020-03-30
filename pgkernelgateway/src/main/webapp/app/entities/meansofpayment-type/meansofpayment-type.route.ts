import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MeansofpaymentType } from 'app/shared/model/meansofpayment-type.model';
import { MeansofpaymentTypeService } from './meansofpayment-type.service';
import { MeansofpaymentTypeComponent } from './meansofpayment-type.component';
import { MeansofpaymentTypeDetailComponent } from './meansofpayment-type-detail.component';
import { MeansofpaymentTypeUpdateComponent } from './meansofpayment-type-update.component';
import { MeansofpaymentTypeDeletePopupComponent } from './meansofpayment-type-delete-dialog.component';
import { IMeansofpaymentType } from 'app/shared/model/meansofpayment-type.model';

@Injectable({ providedIn: 'root' })
export class MeansofpaymentTypeResolve implements Resolve<IMeansofpaymentType> {
  constructor(private service: MeansofpaymentTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMeansofpaymentType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<MeansofpaymentType>) => response.ok),
        map((meansofpaymentType: HttpResponse<MeansofpaymentType>) => meansofpaymentType.body)
      );
    }
    return of(new MeansofpaymentType());
  }
}

export const meansofpaymentTypeRoute: Routes = [
  {
    path: '',
    component: MeansofpaymentTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.meansofpaymentType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MeansofpaymentTypeDetailComponent,
    resolve: {
      meansofpaymentType: MeansofpaymentTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.meansofpaymentType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MeansofpaymentTypeUpdateComponent,
    resolve: {
      meansofpaymentType: MeansofpaymentTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.meansofpaymentType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MeansofpaymentTypeUpdateComponent,
    resolve: {
      meansofpaymentType: MeansofpaymentTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.meansofpaymentType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const meansofpaymentTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MeansofpaymentTypeDeletePopupComponent,
    resolve: {
      meansofpaymentType: MeansofpaymentTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.meansofpaymentType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
