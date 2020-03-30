import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PgMessageModel } from 'app/shared/model/pg-message-model.model';
import { PgMessageModelService } from './pg-message-model.service';
import { PgMessageModelComponent } from './pg-message-model.component';
import { PgMessageModelDetailComponent } from './pg-message-model-detail.component';
import { PgMessageModelUpdateComponent } from './pg-message-model-update.component';
import { PgMessageModelDeletePopupComponent } from './pg-message-model-delete-dialog.component';
import { IPgMessageModel } from 'app/shared/model/pg-message-model.model';

@Injectable({ providedIn: 'root' })
export class PgMessageModelResolve implements Resolve<IPgMessageModel> {
  constructor(private service: PgMessageModelService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPgMessageModel> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PgMessageModel>) => response.ok),
        map((pgMessageModel: HttpResponse<PgMessageModel>) => pgMessageModel.body)
      );
    }
    return of(new PgMessageModel());
  }
}

export const pgMessageModelRoute: Routes = [
  {
    path: '',
    component: PgMessageModelComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PgMessageModelDetailComponent,
    resolve: {
      pgMessageModel: PgMessageModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PgMessageModelUpdateComponent,
    resolve: {
      pgMessageModel: PgMessageModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PgMessageModelUpdateComponent,
    resolve: {
      pgMessageModel: PgMessageModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageModel.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const pgMessageModelPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PgMessageModelDeletePopupComponent,
    resolve: {
      pgMessageModel: PgMessageModelResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.pgMessageModel.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
