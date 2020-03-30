import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgDetailMessage } from 'app/shared/model/pg-detail-message.model';
import { PgDetailMessageService } from './pg-detail-message.service';
import { PgDetailMessageComponent } from './pg-detail-message.component';
import { PgDetailMessageDetailComponent } from './pg-detail-message-detail.component';
import { PgDetailMessageUpdateComponent } from './pg-detail-message-update.component';
import { PgDetailMessageDeletePopupComponent } from './pg-detail-message-delete-dialog.component';
import { IPgDetailMessage } from 'app/shared/model/pg-detail-message.model';

@Injectable({ providedIn: 'root' })
export class PgDetailMessageResolve implements Resolve<IPgDetailMessage> {
  constructor(private service: PgDetailMessageService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgDetailMessage> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgDetailMessage>) => response.ok),
        map((pgDetailMessage: HttpResponse<PgDetailMessage>) => pgDetailMessage.body)
      );
    }
    return of(new PgDetailMessage());
  }
}

export const pgDetailMessageRoute: Routes = [
  {
    path: '',
    component: PgDetailMessageComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgDetailMessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgDetailMessageDetailComponent,
    resolve: {
      pgDetailMessage: PgDetailMessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgDetailMessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgDetailMessageUpdateComponent,
    resolve: {
      pgDetailMessage: PgDetailMessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgDetailMessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgDetailMessageUpdateComponent,
    resolve: {
      pgDetailMessage: PgDetailMessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgDetailMessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgDetailMessagePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgDetailMessageDeletePopupComponent,
    resolve: {
      pgDetailMessage: PgDetailMessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgDetailMessage.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
