import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgResponseCode } from 'app/shared/model/pg-response-code.model';
import { PgResponseCodeService } from './pg-response-code.service';
import { PgResponseCodeComponent } from './pg-response-code.component';
import { PgResponseCodeDetailComponent } from './pg-response-code-detail.component';
import { PgResponseCodeUpdateComponent } from './pg-response-code-update.component';
import { PgResponseCodeDeletePopupComponent } from './pg-response-code-delete-dialog.component';
import { IPgResponseCode } from 'app/shared/model/pg-response-code.model';

@Injectable({ providedIn: 'root' })
export class PgResponseCodeResolve implements Resolve<IPgResponseCode> {
  constructor(private service: PgResponseCodeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgResponseCode> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgResponseCode>) => response.ok),
        map((pgResponseCode: HttpResponse<PgResponseCode>) => pgResponseCode.body)
      );
    }
    return of(new PgResponseCode());
  }
}

export const pgResponseCodeRoute: Routes = [
  {
    path: '',
    component: PgResponseCodeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgResponseCode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgResponseCodeDetailComponent,
    resolve: {
      pgResponseCode: PgResponseCodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgResponseCode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgResponseCodeUpdateComponent,
    resolve: {
      pgResponseCode: PgResponseCodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgResponseCode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgResponseCodeUpdateComponent,
    resolve: {
      pgResponseCode: PgResponseCodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgResponseCode.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgResponseCodePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgResponseCodeDeletePopupComponent,
    resolve: {
      pgResponseCode: PgResponseCodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgResponseCode.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
