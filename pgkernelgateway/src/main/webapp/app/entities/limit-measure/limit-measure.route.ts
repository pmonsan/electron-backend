import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LimitMeasure } from 'app/shared/model/limit-measure.model';
import { LimitMeasureService } from './limit-measure.service';
import { LimitMeasureComponent } from './limit-measure.component';
import { LimitMeasureDetailComponent } from './limit-measure-detail.component';
import { LimitMeasureUpdateComponent } from './limit-measure-update.component';
import { LimitMeasureDeletePopupComponent } from './limit-measure-delete-dialog.component';
import { ILimitMeasure } from 'app/shared/model/limit-measure.model';

@Injectable({ providedIn: 'root' })
export class LimitMeasureResolve implements Resolve<ILimitMeasure> {
  constructor(private service: LimitMeasureService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILimitMeasure> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LimitMeasure>) => response.ok),
        map((limitMeasure: HttpResponse<LimitMeasure>) => limitMeasure.body)
      );
    }
    return of(new LimitMeasure());
  }
}

export const limitMeasureRoute: Routes = [
  {
    path: '',
    component: LimitMeasureComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.limitMeasure.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LimitMeasureDetailComponent,
    resolve: {
      limitMeasure: LimitMeasureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.limitMeasure.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LimitMeasureUpdateComponent,
    resolve: {
      limitMeasure: LimitMeasureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.limitMeasure.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LimitMeasureUpdateComponent,
    resolve: {
      limitMeasure: LimitMeasureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.limitMeasure.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const limitMeasurePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LimitMeasureDeletePopupComponent,
    resolve: {
      limitMeasure: LimitMeasureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.limitMeasure.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
