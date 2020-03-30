import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PartnerSecurity } from 'app/shared/model/partner-security.model';
import { PartnerSecurityService } from './partner-security.service';
import { PartnerSecurityComponent } from './partner-security.component';
import { PartnerSecurityDetailComponent } from './partner-security-detail.component';
import { PartnerSecurityUpdateComponent } from './partner-security-update.component';
import { PartnerSecurityDeletePopupComponent } from './partner-security-delete-dialog.component';
import { IPartnerSecurity } from 'app/shared/model/partner-security.model';

@Injectable({ providedIn: 'root' })
export class PartnerSecurityResolve implements Resolve<IPartnerSecurity> {
  constructor(private service: PartnerSecurityService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPartnerSecurity> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PartnerSecurity>) => response.ok),
        map((partnerSecurity: HttpResponse<PartnerSecurity>) => partnerSecurity.body)
      );
    }
    return of(new PartnerSecurity());
  }
}

export const partnerSecurityRoute: Routes = [
  {
    path: '',
    component: PartnerSecurityComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.partnerSecurity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PartnerSecurityDetailComponent,
    resolve: {
      partnerSecurity: PartnerSecurityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.partnerSecurity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PartnerSecurityUpdateComponent,
    resolve: {
      partnerSecurity: PartnerSecurityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.partnerSecurity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PartnerSecurityUpdateComponent,
    resolve: {
      partnerSecurity: PartnerSecurityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.partnerSecurity.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const partnerSecurityPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PartnerSecurityDeletePopupComponent,
    resolve: {
      partnerSecurity: PartnerSecurityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.partnerSecurity.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
