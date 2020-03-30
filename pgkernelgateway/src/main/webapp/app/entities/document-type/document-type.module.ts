import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  DocumentTypeComponent,
  DocumentTypeDetailComponent,
  DocumentTypeUpdateComponent,
  DocumentTypeDeletePopupComponent,
  DocumentTypeDeleteDialogComponent,
  documentTypeRoute,
  documentTypePopupRoute
} from './';

const ENTITY_STATES = [...documentTypeRoute, ...documentTypePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DocumentTypeComponent,
    DocumentTypeDetailComponent,
    DocumentTypeUpdateComponent,
    DocumentTypeDeleteDialogComponent,
    DocumentTypeDeletePopupComponent
  ],
  entryComponents: [
    DocumentTypeComponent,
    DocumentTypeUpdateComponent,
    DocumentTypeDeleteDialogComponent,
    DocumentTypeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayDocumentTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
