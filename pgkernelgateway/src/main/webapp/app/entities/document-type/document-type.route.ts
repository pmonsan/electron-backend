import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DocumentType } from 'app/shared/model/document-type.model';
import { DocumentTypeService } from './document-type.service';
import { DocumentTypeComponent } from './document-type.component';
import { DocumentTypeDetailComponent } from './document-type-detail.component';
import { DocumentTypeUpdateComponent } from './document-type-update.component';
import { DocumentTypeDeletePopupComponent } from './document-type-delete-dialog.component';
import { IDocumentType } from 'app/shared/model/document-type.model';

@Injectable({ providedIn: 'root' })
export class DocumentTypeResolve implements Resolve<IDocumentType> {
  constructor(private service: DocumentTypeService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDocumentType> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DocumentType>) => response.ok),
        map((documentType: HttpResponse<DocumentType>) => documentType.body)
      );
    }
    return of(new DocumentType());
  }
}

export const documentTypeRoute: Routes = [
  {
    path: '',
    component: DocumentTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.documentType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DocumentTypeDetailComponent,
    resolve: {
      documentType: DocumentTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.documentType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DocumentTypeUpdateComponent,
    resolve: {
      documentType: DocumentTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.documentType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DocumentTypeUpdateComponent,
    resolve: {
      documentType: DocumentTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.documentType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const documentTypePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DocumentTypeDeletePopupComponent,
    resolve: {
      documentType: DocumentTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pgkernelgatewayApp.documentType.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
