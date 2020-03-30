import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pg8583Request } from 'app/shared/model/pg-8583-request.model';
import { Pg8583RequestService } from './pg-8583-request.service';
import { Pg8583RequestComponent } from './pg-8583-request.component';
import { Pg8583RequestDetailComponent } from './pg-8583-request-detail.component';
import { Pg8583RequestUpdateComponent } from './pg-8583-request-update.component';
import { Pg8583RequestDeletePopupComponent } from './pg-8583-request-delete-dialog.component';
import { IPg8583Request } from 'app/shared/model/pg-8583-request.model';

@Injectable({ providedIn: 'root' })
export class Pg8583RequestResolve implements Resolve<IPg8583Request> {
  constructor(private service: Pg8583RequestService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPg8583Request> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Pg8583Request>) => response.ok),
        map((pg8583Request: HttpResponse<Pg8583Request>) => pg8583Request.body)
      );
    }
    return of(new Pg8583Request());
  }
}

export const pg8583RequestRoute: Routes = [
  {
    path: '',
    component: Pg8583RequestComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Request.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: Pg8583RequestDetailComponent,
    resolve: {
      pg8583Request: Pg8583RequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Request.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: Pg8583RequestUpdateComponent,
    resolve: {
      pg8583Request: Pg8583RequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Request.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: Pg8583RequestUpdateComponent,
    resolve: {
      pg8583Request: Pg8583RequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Request.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pg8583RequestPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: Pg8583RequestDeletePopupComponent,
    resolve: {
      pg8583Request: Pg8583RequestResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Request.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
