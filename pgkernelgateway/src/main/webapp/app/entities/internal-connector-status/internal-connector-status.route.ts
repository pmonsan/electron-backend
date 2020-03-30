import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InternalConnectorStatus } from 'app/shared/model/internal-connector-status.model';
import { InternalConnectorStatusService } from './internal-connector-status.service';
import { InternalConnectorStatusComponent } from './internal-connector-status.component';
import { InternalConnectorStatusDetailComponent } from './internal-connector-status-detail.component';
import { InternalConnectorStatusUpdateComponent } from './internal-connector-status-update.component';
import { InternalConnectorStatusDeletePopupComponent } from './internal-connector-status-delete-dialog.component';
import { IInternalConnectorStatus } from 'app/shared/model/internal-connector-status.model';

@Injectable({ providedIn: 'root' })
export class InternalConnectorStatusResolve implements Resolve<IInternalConnectorStatus> {
  constructor(private service: InternalConnectorStatusService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInternalConnectorStatus> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InternalConnectorStatus>) => response.ok),
        map((internalConnectorStatus: HttpResponse<InternalConnectorStatus>) => internalConnectorStatus.body)
      );
    }
    return of(new InternalConnectorStatus());
  }
}

export const internalConnectorStatusRoute: Routes = [
  {
    path: '',
    component: InternalConnectorStatusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.internalConnectorStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InternalConnectorStatusDetailComponent,
    resolve: {
      internalConnectorStatus: InternalConnectorStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.internalConnectorStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InternalConnectorStatusUpdateComponent,
    resolve: {
      internalConnectorStatus: InternalConnectorStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.internalConnectorStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InternalConnectorStatusUpdateComponent,
    resolve: {
      internalConnectorStatus: InternalConnectorStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.internalConnectorStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const internalConnectorStatusPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InternalConnectorStatusDeletePopupComponent,
    resolve: {
      internalConnectorStatus: InternalConnectorStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.internalConnectorStatus.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
