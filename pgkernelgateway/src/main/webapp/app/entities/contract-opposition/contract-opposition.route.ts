import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ContractOpposition } from 'app/shared/model/contract-opposition.model';
import { ContractOppositionService } from './contract-opposition.service';
import { ContractOppositionComponent } from './contract-opposition.component';
import { ContractOppositionDetailComponent } from './contract-opposition-detail.component';
import { ContractOppositionUpdateComponent } from './contract-opposition-update.component';
import { ContractOppositionDeletePopupComponent } from './contract-opposition-delete-dialog.component';
import { IContractOpposition } from 'app/shared/model/contract-opposition.model';

@Injectable({ providedIn: 'root' })
export class ContractOppositionResolve implements Resolve<IContractOpposition> {
  constructor(private service: ContractOppositionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IContractOpposition> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ContractOpposition>) => response.ok),
        map((contractOpposition: HttpResponse<ContractOpposition>) => contractOpposition.body)
      );
    }
    return of(new ContractOpposition());
  }
}

export const contractOppositionRoute: Routes = [
  {
    path: '',
    component: ContractOppositionComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.contractOpposition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContractOppositionDetailComponent,
    resolve: {
      contractOpposition: ContractOppositionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.contractOpposition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContractOppositionUpdateComponent,
    resolve: {
      contractOpposition: ContractOppositionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.contractOpposition.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContractOppositionUpdateComponent,
    resolve: {
      contractOpposition: ContractOppositionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.contractOpposition.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const contractOppositionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ContractOppositionDeletePopupComponent,
    resolve: {
      contractOpposition: ContractOppositionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.contractOpposition.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
