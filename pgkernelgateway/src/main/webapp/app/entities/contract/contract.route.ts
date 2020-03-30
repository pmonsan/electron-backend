import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Contract } from 'app/shared/model/contract.model';
import { ContractService } from './contract.service';
import { ContractComponent } from './contract.component';
import { ContractDetailComponent } from './contract-detail.component';
import { ContractUpdateComponent } from './contract-update.component';
import { ContractDeletePopupComponent } from './contract-delete-dialog.component';
import { IContract } from 'app/shared/model/contract.model';

@Injectable({ providedIn: 'root' })
export class ContractResolve implements Resolve<IContract> {
  constructor(private service: ContractService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContract> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Contract>) => response.ok),
        map((contract: HttpResponse<Contract>) => contract.body)
      );
    }
    return of(new Contract());
  }
}

export const contractRoute: Routes = [
  {
    path: '',
    component: ContractComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.contract.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContractDetailComponent,
    resolve: {
      contract: ContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.contract.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContractUpdateComponent,
    resolve: {
      contract: ContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.contract.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContractUpdateComponent,
    resolve: {
      contract: ContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.contract.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const contractPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ContractDeletePopupComponent,
    resolve: {
      contract: ContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.contract.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
