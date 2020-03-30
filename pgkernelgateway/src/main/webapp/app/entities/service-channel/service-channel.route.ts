import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ServiceChannel } from 'app/shared/model/service-channel.model';
import { ServiceChannelService } from './service-channel.service';
import { ServiceChannelComponent } from './service-channel.component';
import { ServiceChannelDetailComponent } from './service-channel-detail.component';
import { ServiceChannelUpdateComponent } from './service-channel-update.component';
import { ServiceChannelDeletePopupComponent } from './service-channel-delete-dialog.component';
import { IServiceChannel } from 'app/shared/model/service-channel.model';

@Injectable({ providedIn: 'root' })
export class ServiceChannelResolve implements Resolve<IServiceChannel> {
  constructor(private service: ServiceChannelService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IServiceChannel> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ServiceChannel>) => response.ok),
        map((serviceChannel: HttpResponse<ServiceChannel>) => serviceChannel.body)
      );
    }
    return of(new ServiceChannel());
  }
}

export const serviceChannelRoute: Routes = [
  {
    path: '',
    component: ServiceChannelComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ServiceChannelDetailComponent,
    resolve: {
      serviceChannel: ServiceChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ServiceChannelUpdateComponent,
    resolve: {
      serviceChannel: ServiceChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ServiceChannelUpdateComponent,
    resolve: {
      serviceChannel: ServiceChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const serviceChannelPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ServiceChannelDeletePopupComponent,
    resolve: {
      serviceChannel: ServiceChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.serviceChannel.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
