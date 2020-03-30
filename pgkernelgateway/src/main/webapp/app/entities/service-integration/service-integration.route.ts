import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceIntegration } from 'app/shared/model/service-integration.model';
import { ServiceIntegrationService } from './service-integration.service';
import { ServiceIntegrationComponent } from './service-integration.component';
import { ServiceIntegrationDetailComponent } from './service-integration-detail.component';
import { ServiceIntegrationUpdateComponent } from './service-integration-update.component';
import { ServiceIntegrationDeletePopupComponent } from './service-integration-delete-dialog.component';
import { IServiceIntegration } from 'app/shared/model/service-integration.model';

@Injectable({ providedIn: 'root' })
export class ServiceIntegrationResolve implements Resolve<IServiceIntegration> {
  constructor(private service: ServiceIntegrationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceIntegration> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceIntegration>) => response.ok),
        map((serviceIntegration: HttpResponse<ServiceIntegration>) => serviceIntegration.body)
      );
    }
    return of(new ServiceIntegration());
  }
}

export const serviceIntegrationRoute: Routes = [
  {
    path: '',
    component: ServiceIntegrationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceIntegration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceIntegrationDetailComponent,
    resolve: {
      serviceIntegration: ServiceIntegrationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceIntegration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceIntegrationUpdateComponent,
    resolve: {
      serviceIntegration: ServiceIntegrationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceIntegration.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceIntegrationUpdateComponent,
    resolve: {
      serviceIntegration: ServiceIntegrationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceIntegration.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceIntegrationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceIntegrationDeletePopupComponent,
    resolve: {
      serviceIntegration: ServiceIntegrationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceIntegration.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
