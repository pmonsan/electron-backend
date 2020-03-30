import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgModule } from 'app/shared/model/pg-module.model';
import { PgModuleService } from './pg-module.service';
import { PgModuleComponent } from './pg-module.component';
import { PgModuleDetailComponent } from './pg-module-detail.component';
import { PgModuleUpdateComponent } from './pg-module-update.component';
import { PgModuleDeletePopupComponent } from './pg-module-delete-dialog.component';
import { IPgModule } from 'app/shared/model/pg-module.model';

@Injectable({ providedIn: 'root' })
export class PgModuleResolve implements Resolve<IPgModule> {
  constructor(private service: PgModuleService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgModule> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgModule>) => response.ok),
        map((pgModule: HttpResponse<PgModule>) => pgModule.body)
      );
    }
    return of(new PgModule());
  }
}

export const pgModuleRoute: Routes = [
  {
    path: '',
    component: PgModuleComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgModule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgModuleDetailComponent,
    resolve: {
      pgModule: PgModuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgModule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgModuleUpdateComponent,
    resolve: {
      pgModule: PgModuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgModule.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgModuleUpdateComponent,
    resolve: {
      pgModule: PgModuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgModule.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgModulePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgModuleDeletePopupComponent,
    resolve: {
      pgModule: PgModuleResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgModule.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
