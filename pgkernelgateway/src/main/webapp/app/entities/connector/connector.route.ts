import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Connector } from 'app/shared/model/connector.model';
import { ConnectorService } from './connector.service';
import { ConnectorComponent } from './connector.component';
import { ConnectorDetailComponent } from './connector-detail.component';
import { ConnectorUpdateComponent } from './connector-update.component';
import { ConnectorDeletePopupComponent } from './connector-delete-dialog.component';
import { IConnector } from 'app/shared/model/connector.model';

@Injectable({ providedIn: 'root' })
export class ConnectorResolve implements Resolve<IConnector> {
  constructor(private service: ConnectorService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IConnector> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Connector>) => response.ok),
        map((connector: HttpResponse<Connector>) => connector.body)
      );
    }
    return of(new Connector());
  }
}

export const connectorRoute: Routes = [
  {
    path: '',
    component: ConnectorComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.connector.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ConnectorDetailComponent,
    resolve: {
      connector: ConnectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.connector.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ConnectorUpdateComponent,
    resolve: {
      connector: ConnectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.connector.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ConnectorUpdateComponent,
    resolve: {
      connector: ConnectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.connector.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const connectorPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ConnectorDeletePopupComponent,
    resolve: {
      connector: ConnectorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.connector.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
