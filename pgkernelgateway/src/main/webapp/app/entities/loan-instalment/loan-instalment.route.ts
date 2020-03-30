import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { LoanInstalment } from 'app/shared/model/loan-instalment.model';
import { LoanInstalmentService } from './loan-instalment.service';
import { LoanInstalmentComponent } from './loan-instalment.component';
import { LoanInstalmentDetailComponent } from './loan-instalment-detail.component';
import { LoanInstalmentUpdateComponent } from './loan-instalment-update.component';
import { LoanInstalmentDeletePopupComponent } from './loan-instalment-delete-dialog.component';
import { ILoanInstalment } from 'app/shared/model/loan-instalment.model';

@Injectable({ providedIn: 'root' })
export class LoanInstalmentResolve implements Resolve<ILoanInstalment> {
  constructor(private service: LoanInstalmentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ILoanInstalment> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<LoanInstalment>) => response.ok),
        map((loanInstalment: HttpResponse<LoanInstalment>) => loanInstalment.body)
      );
    }
    return of(new LoanInstalment());
  }
}

export const loanInstalmentRoute: Routes = [
  {
    path: '',
    component: LoanInstalmentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.loanInstalment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LoanInstalmentDetailComponent,
    resolve: {
      loanInstalment: LoanInstalmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.loanInstalment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LoanInstalmentUpdateComponent,
    resolve: {
      loanInstalment: LoanInstalmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.loanInstalment.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LoanInstalmentUpdateComponent,
    resolve: {
      loanInstalment: LoanInstalmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.loanInstalment.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const loanInstalmentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: LoanInstalmentDeletePopupComponent,
    resolve: {
      loanInstalment: LoanInstalmentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.loanInstalment.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
