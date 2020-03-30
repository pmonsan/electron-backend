import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgMessage } from 'app/shared/model/pg-message.model';
import { PgMessageService } from './pg-message.service';
import { PgMessageComponent } from './pg-message.component';
import { PgMessageDetailComponent } from './pg-message-detail.component';
import { PgMessageUpdateComponent } from './pg-message-update.component';
import { PgMessageDeletePopupComponent } from './pg-message-delete-dialog.component';
import { IPgMessage } from 'app/shared/model/pg-message.model';

@Injectable({ providedIn: 'root' })
export class PgMessageResolve implements Resolve<IPgMessage> {
  constructor(private service: PgMessageService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgMessage> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgMessage>) => response.ok),
        map((pgMessage: HttpResponse<PgMessage>) => pgMessage.body)
      );
    }
    return of(new PgMessage());
  }
}

export const pgMessageRoute: Routes = [
  {
    path: '',
    component: PgMessageComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgMessageDetailComponent,
    resolve: {
      pgMessage: PgMessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgMessageUpdateComponent,
    resolve: {
      pgMessage: PgMessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgMessageUpdateComponent,
    resolve: {
      pgMessage: PgMessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessage.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgMessagePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgMessageDeletePopupComponent,
    resolve: {
      pgMessage: PgMessageResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessage.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
