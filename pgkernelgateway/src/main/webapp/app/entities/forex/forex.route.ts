import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Forex } from 'app/shared/model/forex.model';
import { ForexService } from './forex.service';
import { ForexComponent } from './forex.component';
import { ForexDetailComponent } from './forex-detail.component';
import { ForexUpdateComponent } from './forex-update.component';
import { ForexDeletePopupComponent } from './forex-delete-dialog.component';
import { IForex } from 'app/shared/model/forex.model';

@Injectable({ providedIn: 'root' })
export class ForexResolve implements Resolve<IForex> {
  constructor(private service: ForexService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IForex> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Forex>) => response.ok),
        map((forex: HttpResponse<Forex>) => forex.body)
      );
    }
    return of(new Forex());
  }
}

export const forexRoute: Routes = [
  {
    path: '',
    component: ForexComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.forex.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ForexDetailComponent,
    resolve: {
      forex: ForexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.forex.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ForexUpdateComponent,
    resolve: {
      forex: ForexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.forex.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ForexUpdateComponent,
    resolve: {
      forex: ForexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.forex.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const forexPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ForexDeletePopupComponent,
    resolve: {
      forex: ForexResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.forex.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
