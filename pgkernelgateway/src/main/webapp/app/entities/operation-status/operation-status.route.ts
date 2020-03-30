import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OperationStatus } from 'app/shared/model/operation-status.model';
import { OperationStatusService } from './operation-status.service';
import { OperationStatusComponent } from './operation-status.component';
import { OperationStatusDetailComponent } from './operation-status-detail.component';
import { OperationStatusUpdateComponent } from './operation-status-update.component';
import { OperationStatusDeletePopupComponent } from './operation-status-delete-dialog.component';
import { IOperationStatus } from 'app/shared/model/operation-status.model';

@Injectable({ providedIn: 'root' })
export class OperationStatusResolve implements Resolve<IOperationStatus> {
  constructor(private service: OperationStatusService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOperationStatus> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OperationStatus>) => response.ok),
        map((operationStatus: HttpResponse<OperationStatus>) => operationStatus.body)
      );
    }
    return of(new OperationStatus());
  }
}

export const operationStatusRoute: Routes = [
  {
    path: '',
    component: OperationStatusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.operationStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OperationStatusDetailComponent,
    resolve: {
      operationStatus: OperationStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.operationStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OperationStatusUpdateComponent,
    resolve: {
      operationStatus: OperationStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.operationStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OperationStatusUpdateComponent,
    resolve: {
      operationStatus: OperationStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.operationStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const operationStatusPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OperationStatusDeletePopupComponent,
    resolve: {
      operationStatus: OperationStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.operationStatus.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
