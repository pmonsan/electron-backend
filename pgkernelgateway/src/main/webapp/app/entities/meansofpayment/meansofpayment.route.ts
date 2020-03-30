import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Meansofpayment } from 'app/shared/model/meansofpayment.model';
import { MeansofpaymentService } from './meansofpayment.service';
import { MeansofpaymentComponent } from './meansofpayment.component';
import { MeansofpaymentDetailComponent } from './meansofpayment-detail.component';
import { MeansofpaymentUpdateComponent } from './meansofpayment-update.component';
import { MeansofpaymentDeletePopupComponent } from './meansofpayment-delete-dialog.component';
import { IMeansofpayment } from 'app/shared/model/meansofpayment.model';

@Injectable({ providedIn: 'root' })
export class MeansofpaymentResolve implements Resolve<IMeansofpayment> {
  constructor(private service: MeansofpaymentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMeansofpayment> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Meansofpayment>) => response.ok),
        map((meansofpayment: HttpResponse<Meansofpayment>) => meansofpayment.body)
      );
    }
    return of(new Meansofpayment());
  }
}

export const meansofpaymentRoute: Routes = [
  {
    path: '',
    component: MeansofpaymentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.meansofpayment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: MeansofpaymentDetailComponent,
    resolve: {
      meansofpayment: MeansofpaymentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.meansofpayment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: MeansofpaymentUpdateComponent,
    resolve: {
      meansofpayment: MeansofpaymentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.meansofpayment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: MeansofpaymentUpdateComponent,
    resolve: {
      meansofpayment: MeansofpaymentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.meansofpayment.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const meansofpaymentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: MeansofpaymentDeletePopupComponent,
    resolve: {
      meansofpayment: MeansofpaymentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.meansofpayment.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
