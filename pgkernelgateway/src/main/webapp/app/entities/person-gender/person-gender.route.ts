import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PersonGender } from 'app/shared/model/person-gender.model';
import { PersonGenderService } from './person-gender.service';
import { PersonGenderComponent } from './person-gender.component';
import { PersonGenderDetailComponent } from './person-gender-detail.component';
import { PersonGenderUpdateComponent } from './person-gender-update.component';
import { PersonGenderDeletePopupComponent } from './person-gender-delete-dialog.component';
import { IPersonGender } from 'app/shared/model/person-gender.model';

@Injectable({ providedIn: 'root' })
export class PersonGenderResolve implements Resolve<IPersonGender> {
  constructor(private service: PersonGenderService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPersonGender> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PersonGender>) => response.ok),
        map((personGender: HttpResponse<PersonGender>) => personGender.body)
      );
    }
    return of(new PersonGender());
  }
}

export const personGenderRoute: Routes = [
  {
    path: '',
    component: PersonGenderComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personGender.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PersonGenderDetailComponent,
    resolve: {
      personGender: PersonGenderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personGender.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PersonGenderUpdateComponent,
    resolve: {
      personGender: PersonGenderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personGender.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PersonGenderUpdateComponent,
    resolve: {
      personGender: PersonGenderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personGender.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const personGenderPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PersonGenderDeletePopupComponent,
    resolve: {
      personGender: PersonGenderResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personGender.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
