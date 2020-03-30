import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ConnectorType } from 'app/shared/model/connector-type.model';
import { ConnectorTypeService } from './connector-type.service';
import { ConnectorTypeComponent } from './connector-type.component';
import { ConnectorTypeDetailComponent } from './connector-type-detail.component';
import { ConnectorTypeUpdateComponent } from './connector-type-update.component';
import { ConnectorTypeDeletePopupComponent } from './connector-type-delete-dialog.component';
import { IConnectorType } from 'app/shared/model/connector-type.model';

@Injectable({ providedIn: 'root' })
export class ConnectorTypeResolve implements Resolve<IConnectorType> {
  constructor(private service: ConnectorTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IConnectorType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ConnectorType>) => response.ok),
        map((connectorType: HttpResponse<ConnectorType>) => connectorType.body)
      );
    }
    return of(new ConnectorType());
  }
}

export const connectorTypeRoute: Routes = [
  {
    path: '',
    component: ConnectorTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.connectorType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ConnectorTypeDetailComponent,
    resolve: {
      connectorType: ConnectorTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.connectorType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ConnectorTypeUpdateComponent,
    resolve: {
      connectorType: ConnectorTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.connectorType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ConnectorTypeUpdateComponent,
    resolve: {
      connectorType: ConnectorTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.connectorType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const connectorTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ConnectorTypeDeletePopupComponent,
    resolve: {
      connectorType: ConnectorTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.connectorType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
