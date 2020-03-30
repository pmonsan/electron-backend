import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PartnerType } from 'app/shared/model/partner-type.model';
import { PartnerTypeService } from './partner-type.service';
import { PartnerTypeComponent } from './partner-type.component';
import { PartnerTypeDetailComponent } from './partner-type-detail.component';
import { PartnerTypeUpdateComponent } from './partner-type-update.component';
import { PartnerTypeDeletePopupComponent } from './partner-type-delete-dialog.component';
import { IPartnerType } from 'app/shared/model/partner-type.model';

@Injectable({ providedIn: 'root' })
export class PartnerTypeResolve implements Resolve<IPartnerType> {
  constructor(private service: PartnerTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPartnerType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PartnerType>) => response.ok),
        map((partnerType: HttpResponse<PartnerType>) => partnerType.body)
      );
    }
    return of(new PartnerType());
  }
}

export const partnerTypeRoute: Routes = [
  {
    path: '',
    component: PartnerTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.partnerType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PartnerTypeDetailComponent,
    resolve: {
      partnerType: PartnerTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.partnerType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PartnerTypeUpdateComponent,
    resolve: {
      partnerType: PartnerTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.partnerType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PartnerTypeUpdateComponent,
    resolve: {
      partnerType: PartnerTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.partnerType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const partnerTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PartnerTypeDeletePopupComponent,
    resolve: {
      partnerType: PartnerTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.partnerType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
