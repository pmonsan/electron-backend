import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { UserConnection } from 'app/shared/model/user-connection.model';
import { UserConnectionService } from './user-connection.service';
import { UserConnectionComponent } from './user-connection.component';
import { UserConnectionDetailComponent } from './user-connection-detail.component';
import { UserConnectionUpdateComponent } from './user-connection-update.component';
import { UserConnectionDeletePopupComponent } from './user-connection-delete-dialog.component';
import { IUserConnection } from 'app/shared/model/user-connection.model';

@Injectable({ providedIn: 'root' })
export class UserConnectionResolve implements Resolve<IUserConnection> {
  constructor(private service: UserConnectionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IUserConnection> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<UserConnection>) => response.ok),
        map((userConnection: HttpResponse<UserConnection>) => userConnection.body)
      );
    }
    return of(new UserConnection());
  }
}

export const userConnectionRoute: Routes = [
  {
    path: '',
    component: UserConnectionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: UserConnectionDetailComponent,
    resolve: {
      userConnection: UserConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: UserConnectionUpdateComponent,
    resolve: {
      userConnection: UserConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: UserConnectionUpdateComponent,
    resolve: {
      userConnection: UserConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userConnection.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const userConnectionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: UserConnectionDeletePopupComponent,
    resolve: {
      userConnection: UserConnectionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.userConnection.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
