import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DetailContract } from 'app/shared/model/detail-contract.model';
import { DetailContractService } from './detail-contract.service';
import { DetailContractComponent } from './detail-contract.component';
import { DetailContractDetailComponent } from './detail-contract-detail.component';
import { DetailContractUpdateComponent } from './detail-contract-update.component';
import { DetailContractDeletePopupComponent } from './detail-contract-delete-dialog.component';
import { IDetailContract } from 'app/shared/model/detail-contract.model';

@Injectable({ providedIn: 'root' })
export class DetailContractResolve implements Resolve<IDetailContract> {
  constructor(private service: DetailContractService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDetailContract> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DetailContract>) => response.ok),
        map((detailContract: HttpResponse<DetailContract>) => detailContract.body)
      );
    }
    return of(new DetailContract());
  }
}

export const detailContractRoute: Routes = [
  {
    path: '',
    component: DetailContractComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.detailContract.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DetailContractDetailComponent,
    resolve: {
      detailContract: DetailContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.detailContract.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DetailContractUpdateComponent,
    resolve: {
      detailContract: DetailContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.detailContract.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DetailContractUpdateComponent,
    resolve: {
      detailContract: DetailContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.detailContract.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const detailContractPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DetailContractDeletePopupComponent,
    resolve: {
      detailContract: DetailContractResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.detailContract.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
