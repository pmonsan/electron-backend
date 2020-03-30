import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Beneficiary } from 'app/shared/model/beneficiary.model';
import { BeneficiaryService } from './beneficiary.service';
import { BeneficiaryComponent } from './beneficiary.component';
import { BeneficiaryDetailComponent } from './beneficiary-detail.component';
import { BeneficiaryUpdateComponent } from './beneficiary-update.component';
import { BeneficiaryDeletePopupComponent } from './beneficiary-delete-dialog.component';
import { IBeneficiary } from 'app/shared/model/beneficiary.model';

@Injectable({ providedIn: 'root' })
export class BeneficiaryResolve implements Resolve<IBeneficiary> {
  constructor(private service: BeneficiaryService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBeneficiary> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Beneficiary>) => response.ok),
        map((beneficiary: HttpResponse<Beneficiary>) => beneficiary.body)
      );
    }
    return of(new Beneficiary());
  }
}

export const beneficiaryRoute: Routes = [
  {
    path: '',
    component: BeneficiaryComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiary.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BeneficiaryDetailComponent,
    resolve: {
      beneficiary: BeneficiaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiary.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BeneficiaryUpdateComponent,
    resolve: {
      beneficiary: BeneficiaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiary.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BeneficiaryUpdateComponent,
    resolve: {
      beneficiary: BeneficiaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiary.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const beneficiaryPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BeneficiaryDeletePopupComponent,
    resolve: {
      beneficiary: BeneficiaryResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiary.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
