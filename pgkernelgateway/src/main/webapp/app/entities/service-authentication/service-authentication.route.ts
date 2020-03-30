import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceAuthentication } from 'app/shared/model/service-authentication.model';
import { ServiceAuthenticationService } from './service-authentication.service';
import { ServiceAuthenticationComponent } from './service-authentication.component';
import { ServiceAuthenticationDetailComponent } from './service-authentication-detail.component';
import { ServiceAuthenticationUpdateComponent } from './service-authentication-update.component';
import { ServiceAuthenticationDeletePopupComponent } from './service-authentication-delete-dialog.component';
import { IServiceAuthentication } from 'app/shared/model/service-authentication.model';

@Injectable({ providedIn: 'root' })
export class ServiceAuthenticationResolve implements Resolve<IServiceAuthentication> {
  constructor(private service: ServiceAuthenticationService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceAuthentication> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceAuthentication>) => response.ok),
        map((serviceAuthentication: HttpResponse<ServiceAuthentication>) => serviceAuthentication.body)
      );
    }
    return of(new ServiceAuthentication());
  }
}

export const serviceAuthenticationRoute: Routes = [
  {
    path: '',
    component: ServiceAuthenticationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceAuthentication.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceAuthenticationDetailComponent,
    resolve: {
      serviceAuthentication: ServiceAuthenticationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceAuthentication.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceAuthenticationUpdateComponent,
    resolve: {
      serviceAuthentication: ServiceAuthenticationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceAuthentication.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceAuthenticationUpdateComponent,
    resolve: {
      serviceAuthentication: ServiceAuthenticationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceAuthentication.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceAuthenticationPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceAuthenticationDeletePopupComponent,
    resolve: {
      serviceAuthentication: ServiceAuthenticationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceAuthentication.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
