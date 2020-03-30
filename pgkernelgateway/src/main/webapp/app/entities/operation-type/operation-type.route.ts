import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { OperationType } from 'app/shared/model/operation-type.model';
import { OperationTypeService } from './operation-type.service';
import { OperationTypeComponent } from './operation-type.component';
import { OperationTypeDetailComponent } from './operation-type-detail.component';
import { OperationTypeUpdateComponent } from './operation-type-update.component';
import { OperationTypeDeletePopupComponent } from './operation-type-delete-dialog.component';
import { IOperationType } from 'app/shared/model/operation-type.model';

@Injectable({ providedIn: 'root' })
export class OperationTypeResolve implements Resolve<IOperationType> {
  constructor(private service: OperationTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOperationType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<OperationType>) => response.ok),
        map((operationType: HttpResponse<OperationType>) => operationType.body)
      );
    }
    return of(new OperationType());
  }
}

export const operationTypeRoute: Routes = [
  {
    path: '',
    component: OperationTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.operationType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OperationTypeDetailComponent,
    resolve: {
      operationType: OperationTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.operationType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OperationTypeUpdateComponent,
    resolve: {
      operationType: OperationTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.operationType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OperationTypeUpdateComponent,
    resolve: {
      operationType: OperationTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.operationType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const operationTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OperationTypeDeletePopupComponent,
    resolve: {
      operationType: OperationTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.operationType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
