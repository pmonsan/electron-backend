import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BeneficiaryType } from 'app/shared/model/beneficiary-type.model';
import { BeneficiaryTypeService } from './beneficiary-type.service';
import { BeneficiaryTypeComponent } from './beneficiary-type.component';
import { BeneficiaryTypeDetailComponent } from './beneficiary-type-detail.component';
import { BeneficiaryTypeUpdateComponent } from './beneficiary-type-update.component';
import { BeneficiaryTypeDeletePopupComponent } from './beneficiary-type-delete-dialog.component';
import { IBeneficiaryType } from 'app/shared/model/beneficiary-type.model';

@Injectable({ providedIn: 'root' })
export class BeneficiaryTypeResolve implements Resolve<IBeneficiaryType> {
  constructor(private service: BeneficiaryTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBeneficiaryType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BeneficiaryType>) => response.ok),
        map((beneficiaryType: HttpResponse<BeneficiaryType>) => beneficiaryType.body)
      );
    }
    return of(new BeneficiaryType());
  }
}

export const beneficiaryTypeRoute: Routes = [
  {
    path: '',
    component: BeneficiaryTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiaryType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BeneficiaryTypeDetailComponent,
    resolve: {
      beneficiaryType: BeneficiaryTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiaryType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BeneficiaryTypeUpdateComponent,
    resolve: {
      beneficiaryType: BeneficiaryTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiaryType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BeneficiaryTypeUpdateComponent,
    resolve: {
      beneficiaryType: BeneficiaryTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiaryType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const beneficiaryTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BeneficiaryTypeDeletePopupComponent,
    resolve: {
      beneficiaryType: BeneficiaryTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiaryType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
