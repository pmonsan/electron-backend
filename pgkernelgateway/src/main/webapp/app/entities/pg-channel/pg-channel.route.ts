import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgChannel } from 'app/shared/model/pg-channel.model';
import { PgChannelService } from './pg-channel.service';
import { PgChannelComponent } from './pg-channel.component';
import { PgChannelDetailComponent } from './pg-channel-detail.component';
import { PgChannelUpdateComponent } from './pg-channel-update.component';
import { PgChannelDeletePopupComponent } from './pg-channel-delete-dialog.component';
import { IPgChannel } from 'app/shared/model/pg-channel.model';

@Injectable({ providedIn: 'root' })
export class PgChannelResolve implements Resolve<IPgChannel> {
  constructor(private service: PgChannelService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgChannel> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgChannel>) => response.ok),
        map((pgChannel: HttpResponse<PgChannel>) => pgChannel.body)
      );
    }
    return of(new PgChannel());
  }
}

export const pgChannelRoute: Routes = [
  {
    path: '',
    component: PgChannelComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgChannelDetailComponent,
    resolve: {
      pgChannel: PgChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgChannelUpdateComponent,
    resolve: {
      pgChannel: PgChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgChannelUpdateComponent,
    resolve: {
      pgChannel: PgChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgChannel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgChannelPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgChannelDeletePopupComponent,
    resolve: {
      pgChannel: PgChannelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgChannel.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
