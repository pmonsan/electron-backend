import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Pg8583Callback } from 'app/shared/model/pg-8583-callback.model';
import { Pg8583CallbackService } from './pg-8583-callback.service';
import { Pg8583CallbackComponent } from './pg-8583-callback.component';
import { Pg8583CallbackDetailComponent } from './pg-8583-callback-detail.component';
import { Pg8583CallbackUpdateComponent } from './pg-8583-callback-update.component';
import { Pg8583CallbackDeletePopupComponent } from './pg-8583-callback-delete-dialog.component';
import { IPg8583Callback } from 'app/shared/model/pg-8583-callback.model';

@Injectable({ providedIn: 'root' })
export class Pg8583CallbackResolve implements Resolve<IPg8583Callback> {
  constructor(private service: Pg8583CallbackService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPg8583Callback> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Pg8583Callback>) => response.ok),
        map((pg8583Callback: HttpResponse<Pg8583Callback>) => pg8583Callback.body)
      );
    }
    return of(new Pg8583Callback());
  }
}

export const pg8583CallbackRoute: Routes = [
  {
    path: '',
    component: Pg8583CallbackComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Callback.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: Pg8583CallbackDetailComponent,
    resolve: {
      pg8583Callback: Pg8583CallbackResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Callback.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: Pg8583CallbackUpdateComponent,
    resolve: {
      pg8583Callback: Pg8583CallbackResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Callback.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: Pg8583CallbackUpdateComponent,
    resolve: {
      pg8583Callback: Pg8583CallbackResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Callback.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pg8583CallbackPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: Pg8583CallbackDeletePopupComponent,
    resolve: {
      pg8583Callback: Pg8583CallbackResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pg8583Callback.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
