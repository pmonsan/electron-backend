import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LoanInstalmentStatus } from 'app/shared/model/loan-instalment-status.model';
import { LoanInstalmentStatusService } from './loan-instalment-status.service';
import { LoanInstalmentStatusComponent } from './loan-instalment-status.component';
import { LoanInstalmentStatusDetailComponent } from './loan-instalment-status-detail.component';
import { LoanInstalmentStatusUpdateComponent } from './loan-instalment-status-update.component';
import { LoanInstalmentStatusDeletePopupComponent } from './loan-instalment-status-delete-dialog.component';
import { ILoanInstalmentStatus } from 'app/shared/model/loan-instalment-status.model';

@Injectable({ providedIn: 'root' })
export class LoanInstalmentStatusResolve implements Resolve<ILoanInstalmentStatus> {
  constructor(private service: LoanInstalmentStatusService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILoanInstalmentStatus> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LoanInstalmentStatus>) => response.ok),
        map((loanInstalmentStatus: HttpResponse<LoanInstalmentStatus>) => loanInstalmentStatus.body)
      );
    }
    return of(new LoanInstalmentStatus());
  }
}

export const loanInstalmentStatusRoute: Routes = [
  {
    path: '',
    component: LoanInstalmentStatusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.loanInstalmentStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LoanInstalmentStatusDetailComponent,
    resolve: {
      loanInstalmentStatus: LoanInstalmentStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.loanInstalmentStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LoanInstalmentStatusUpdateComponent,
    resolve: {
      loanInstalmentStatus: LoanInstalmentStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.loanInstalmentStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LoanInstalmentStatusUpdateComponent,
    resolve: {
      loanInstalmentStatus: LoanInstalmentStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.loanInstalmentStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const loanInstalmentStatusPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LoanInstalmentStatusDeletePopupComponent,
    resolve: {
      loanInstalmentStatus: LoanInstalmentStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.loanInstalmentStatus.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
