import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Periodicity } from 'app/shared/model/periodicity.model';
import { PeriodicityService } from './periodicity.service';
import { PeriodicityComponent } from './periodicity.component';
import { PeriodicityDetailComponent } from './periodicity-detail.component';
import { PeriodicityUpdateComponent } from './periodicity-update.component';
import { PeriodicityDeletePopupComponent } from './periodicity-delete-dialog.component';
import { IPeriodicity } from 'app/shared/model/periodicity.model';

@Injectable({ providedIn: 'root' })
export class PeriodicityResolve implements Resolve<IPeriodicity> {
  constructor(private service: PeriodicityService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPeriodicity> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Periodicity>) => response.ok),
        map((periodicity: HttpResponse<Periodicity>) => periodicity.body)
      );
    }
    return of(new Periodicity());
  }
}

export const periodicityRoute: Routes = [
  {
    path: '',
    component: PeriodicityComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.periodicity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PeriodicityDetailComponent,
    resolve: {
      periodicity: PeriodicityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.periodicity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PeriodicityUpdateComponent,
    resolve: {
      periodicity: PeriodicityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.periodicity.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PeriodicityUpdateComponent,
    resolve: {
      periodicity: PeriodicityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.periodicity.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const periodicityPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PeriodicityDeletePopupComponent,
    resolve: {
      periodicity: PeriodicityResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.periodicity.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
