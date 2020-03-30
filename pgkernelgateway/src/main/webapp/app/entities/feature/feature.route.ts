import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Feature } from 'app/shared/model/feature.model';
import { FeatureService } from './feature.service';
import { FeatureComponent } from './feature.component';
import { FeatureDetailComponent } from './feature-detail.component';
import { FeatureUpdateComponent } from './feature-update.component';
import { FeatureDeletePopupComponent } from './feature-delete-dialog.component';
import { IFeature } from 'app/shared/model/feature.model';

@Injectable({ providedIn: 'root' })
export class FeatureResolve implements Resolve<IFeature> {
  constructor(private service: FeatureService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFeature> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Feature>) => response.ok),
        map((feature: HttpResponse<Feature>) => feature.body)
      );
    }
    return of(new Feature());
  }
}

export const featureRoute: Routes = [
  {
    path: '',
    component: FeatureComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.feature.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FeatureDetailComponent,
    resolve: {
      feature: FeatureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.feature.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FeatureUpdateComponent,
    resolve: {
      feature: FeatureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.feature.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FeatureUpdateComponent,
    resolve: {
      feature: FeatureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.feature.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const featurePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FeatureDeletePopupComponent,
    resolve: {
      feature: FeatureResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.feature.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
