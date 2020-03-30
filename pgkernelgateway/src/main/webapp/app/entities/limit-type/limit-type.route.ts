import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LimitType } from 'app/shared/model/limit-type.model';
import { LimitTypeService } from './limit-type.service';
import { LimitTypeComponent } from './limit-type.component';
import { LimitTypeDetailComponent } from './limit-type-detail.component';
import { LimitTypeUpdateComponent } from './limit-type-update.component';
import { LimitTypeDeletePopupComponent } from './limit-type-delete-dialog.component';
import { ILimitType } from 'app/shared/model/limit-type.model';

@Injectable({ providedIn: 'root' })
export class LimitTypeResolve implements Resolve<ILimitType> {
  constructor(private service: LimitTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILimitType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LimitType>) => response.ok),
        map((limitType: HttpResponse<LimitType>) => limitType.body)
      );
    }
    return of(new LimitType());
  }
}

export const limitTypeRoute: Routes = [
  {
    path: '',
    component: LimitTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.limitType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LimitTypeDetailComponent,
    resolve: {
      limitType: LimitTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.limitType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LimitTypeUpdateComponent,
    resolve: {
      limitType: LimitTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.limitType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LimitTypeUpdateComponent,
    resolve: {
      limitType: LimitTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.limitType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const limitTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LimitTypeDeletePopupComponent,
    resolve: {
      limitType: LimitTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.limitType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
