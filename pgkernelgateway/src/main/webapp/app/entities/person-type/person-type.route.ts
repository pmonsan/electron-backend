import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PersonType } from 'app/shared/model/person-type.model';
import { PersonTypeService } from './person-type.service';
import { PersonTypeComponent } from './person-type.component';
import { PersonTypeDetailComponent } from './person-type-detail.component';
import { PersonTypeUpdateComponent } from './person-type-update.component';
import { PersonTypeDeletePopupComponent } from './person-type-delete-dialog.component';
import { IPersonType } from 'app/shared/model/person-type.model';

@Injectable({ providedIn: 'root' })
export class PersonTypeResolve implements Resolve<IPersonType> {
  constructor(private service: PersonTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPersonType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PersonType>) => response.ok),
        map((personType: HttpResponse<PersonType>) => personType.body)
      );
    }
    return of(new PersonType());
  }
}

export const personTypeRoute: Routes = [
  {
    path: '',
    component: PersonTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PersonTypeDetailComponent,
    resolve: {
      personType: PersonTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PersonTypeUpdateComponent,
    resolve: {
      personType: PersonTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PersonTypeUpdateComponent,
    resolve: {
      personType: PersonTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const personTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PersonTypeDeletePopupComponent,
    resolve: {
      personType: PersonTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
