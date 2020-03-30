import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgMessageStatus } from 'app/shared/model/pg-message-status.model';
import { PgMessageStatusService } from './pg-message-status.service';
import { PgMessageStatusComponent } from './pg-message-status.component';
import { PgMessageStatusDetailComponent } from './pg-message-status-detail.component';
import { PgMessageStatusUpdateComponent } from './pg-message-status-update.component';
import { PgMessageStatusDeletePopupComponent } from './pg-message-status-delete-dialog.component';
import { IPgMessageStatus } from 'app/shared/model/pg-message-status.model';

@Injectable({ providedIn: 'root' })
export class PgMessageStatusResolve implements Resolve<IPgMessageStatus> {
  constructor(private service: PgMessageStatusService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgMessageStatus> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgMessageStatus>) => response.ok),
        map((pgMessageStatus: HttpResponse<PgMessageStatus>) => pgMessageStatus.body)
      );
    }
    return of(new PgMessageStatus());
  }
}

export const pgMessageStatusRoute: Routes = [
  {
    path: '',
    component: PgMessageStatusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgMessageStatusDetailComponent,
    resolve: {
      pgMessageStatus: PgMessageStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgMessageStatusUpdateComponent,
    resolve: {
      pgMessageStatus: PgMessageStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgMessageStatusUpdateComponent,
    resolve: {
      pgMessageStatus: PgMessageStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgMessageStatusPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgMessageStatusDeletePopupComponent,
    resolve: {
      pgMessageStatus: PgMessageStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageStatus.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
