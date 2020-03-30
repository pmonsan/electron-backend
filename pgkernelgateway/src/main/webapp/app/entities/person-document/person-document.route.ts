import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { PersonDocument } from 'app/shared/model/person-document.model';
import { PersonDocumentService } from './person-document.service';
import { PersonDocumentComponent } from './person-document.component';
import { PersonDocumentDetailComponent } from './person-document-detail.component';
import { PersonDocumentUpdateComponent } from './person-document-update.component';
import { PersonDocumentDeletePopupComponent } from './person-document-delete-dialog.component';
import { IPersonDocument } from 'app/shared/model/person-document.model';

@Injectable({ providedIn: 'root' })
export class PersonDocumentResolve implements Resolve<IPersonDocument> {
  constructor(private service: PersonDocumentService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IPersonDocument> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<PersonDocument>) => response.ok),
        map((personDocument: HttpResponse<PersonDocument>) => personDocument.body)
      );
    }
    return of(new PersonDocument());
  }
}

export const personDocumentRoute: Routes = [
  {
    path: '',
    component: PersonDocumentComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PersonDocumentDetailComponent,
    resolve: {
      personDocument: PersonDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PersonDocumentUpdateComponent,
    resolve: {
      personDocument: PersonDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PersonDocumentUpdateComponent,
    resolve: {
      personDocument: PersonDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personDocument.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const personDocumentPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: PersonDocumentDeletePopupComponent,
    resolve: {
      personDocument: PersonDocumentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.personDocument.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
