import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { InternalConnectorRequest } from 'app/shared/model/internal-connector-request.model';
import { InternalConnectorRequestService } from './internal-connector-request.service';
import { InternalConnectorRequestComponent } from './internal-connector-request.component';
import { InternalConnectorRequestDetailComponent } from './internal-connector-request-detail.component';
import { InternalConnectorRequestUpdateComponent } from './internal-connector-request-update.component';
import { InternalConnectorRequestDeletePopupComponent } from './internal-connector-request-delete-dialog.component';
import { IInternalConnectorRequest } from 'app/shared/model/internal-connector-request.model';

@Injectable({ providedIn: 'root' })
export class InternalConnectorRequestResolve implements Resolve<IInternalConnectorRequest> {
  constructor(private service: InternalConnectorRequestService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IInternalConnectorRequest> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<InternalConnectorRequest>) => response.ok),
        map((internalConnectorRequest: HttpResponse<InternalConnectorRequest>) => internalConnectorRequest.body)
      );
    }
    return of(new InternalConnectorRequest());
  }
}

export const internalConnectorRequestRoute: Routes = [
  {
    path: '',
    component: InternalConnectorRequestComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.internalConnectorRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: InternalConnectorRequestDetailComponent,
    resolve: {
      internalConnectorRequest: InternalConnectorRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.internalConnectorRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: InternalConnectorRequestUpdateComponent,
    resolve: {
      internalConnectorRequest: InternalConnectorRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.internalConnectorRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: InternalConnectorRequestUpdateComponent,
    resolve: {
      internalConnectorRequest: InternalConnectorRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.internalConnectorRequest.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const internalConnectorRequestPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: InternalConnectorRequestDeletePopupComponent,
    resolve: {
      internalConnectorRequest: InternalConnectorRequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.internalConnectorRequest.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
