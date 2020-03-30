import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pg8583Status } from 'app/shared/model/pg-8583-status.model';
import { Pg8583StatusService } from './pg-8583-status.service';
import { Pg8583StatusComponent } from './pg-8583-status.component';
import { Pg8583StatusDetailComponent } from './pg-8583-status-detail.component';
import { Pg8583StatusUpdateComponent } from './pg-8583-status-update.component';
import { Pg8583StatusDeletePopupComponent } from './pg-8583-status-delete-dialog.component';
import { IPg8583Status } from 'app/shared/model/pg-8583-status.model';

@Injectable({ providedIn: 'root' })
export class Pg8583StatusResolve implements Resolve<IPg8583Status> {
  constructor(private service: Pg8583StatusService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPg8583Status> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Pg8583Status>) => response.ok),
        map((pg8583Status: HttpResponse<Pg8583Status>) => pg8583Status.body)
      );
    }
    return of(new Pg8583Status());
  }
}

export const pg8583StatusRoute: Routes = [
  {
    path: '',
    component: Pg8583StatusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Status.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: Pg8583StatusDetailComponent,
    resolve: {
      pg8583Status: Pg8583StatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Status.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: Pg8583StatusUpdateComponent,
    resolve: {
      pg8583Status: Pg8583StatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Status.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: Pg8583StatusUpdateComponent,
    resolve: {
      pg8583Status: Pg8583StatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Status.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pg8583StatusPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: Pg8583StatusDeletePopupComponent,
    resolve: {
      pg8583Status: Pg8583StatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Status.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
