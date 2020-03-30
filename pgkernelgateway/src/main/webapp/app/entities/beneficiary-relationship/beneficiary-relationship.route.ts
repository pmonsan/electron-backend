import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BeneficiaryRelationship } from 'app/shared/model/beneficiary-relationship.model';
import { BeneficiaryRelationshipService } from './beneficiary-relationship.service';
import { BeneficiaryRelationshipComponent } from './beneficiary-relationship.component';
import { BeneficiaryRelationshipDetailComponent } from './beneficiary-relationship-detail.component';
import { BeneficiaryRelationshipUpdateComponent } from './beneficiary-relationship-update.component';
import { BeneficiaryRelationshipDeletePopupComponent } from './beneficiary-relationship-delete-dialog.component';
import { IBeneficiaryRelationship } from 'app/shared/model/beneficiary-relationship.model';

@Injectable({ providedIn: 'root' })
export class BeneficiaryRelationshipResolve implements Resolve<IBeneficiaryRelationship> {
  constructor(private service: BeneficiaryRelationshipService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBeneficiaryRelationship> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<BeneficiaryRelationship>) => response.ok),
        map((beneficiaryRelationship: HttpResponse<BeneficiaryRelationship>) => beneficiaryRelationship.body)
      );
    }
    return of(new BeneficiaryRelationship());
  }
}

export const beneficiaryRelationshipRoute: Routes = [
  {
    path: '',
    component: BeneficiaryRelationshipComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiaryRelationship.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BeneficiaryRelationshipDetailComponent,
    resolve: {
      beneficiaryRelationship: BeneficiaryRelationshipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiaryRelationship.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BeneficiaryRelationshipUpdateComponent,
    resolve: {
      beneficiaryRelationship: BeneficiaryRelationshipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiaryRelationship.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BeneficiaryRelationshipUpdateComponent,
    resolve: {
      beneficiaryRelationship: BeneficiaryRelationshipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiaryRelationship.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const beneficiaryRelationshipPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BeneficiaryRelationshipDeletePopupComponent,
    resolve: {
      beneficiaryRelationship: BeneficiaryRelationshipResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.beneficiaryRelationship.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
