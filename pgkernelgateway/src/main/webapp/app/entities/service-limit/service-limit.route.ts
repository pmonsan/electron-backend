import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceLimit } from 'app/shared/model/service-limit.model';
import { ServiceLimitService } from './service-limit.service';
import { ServiceLimitComponent } from './service-limit.component';
import { ServiceLimitDetailComponent } from './service-limit-detail.component';
import { ServiceLimitUpdateComponent } from './service-limit-update.component';
import { ServiceLimitDeletePopupComponent } from './service-limit-delete-dialog.component';
import { IServiceLimit } from 'app/shared/model/service-limit.model';

@Injectable({ providedIn: 'root' })
export class ServiceLimitResolve implements Resolve<IServiceLimit> {
  constructor(private service: ServiceLimitService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceLimit> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceLimit>) => response.ok),
        map((serviceLimit: HttpResponse<ServiceLimit>) => serviceLimit.body)
      );
    }
    return of(new ServiceLimit());
  }
}

export const serviceLimitRoute: Routes = [
  {
    path: '',
    component: ServiceLimitComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceLimit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceLimitDetailComponent,
    resolve: {
      serviceLimit: ServiceLimitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceLimit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceLimitUpdateComponent,
    resolve: {
      serviceLimit: ServiceLimitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceLimit.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceLimitUpdateComponent,
    resolve: {
      serviceLimit: ServiceLimitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceLimit.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceLimitPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceLimitDeletePopupComponent,
    resolve: {
      serviceLimit: ServiceLimitResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceLimit.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
