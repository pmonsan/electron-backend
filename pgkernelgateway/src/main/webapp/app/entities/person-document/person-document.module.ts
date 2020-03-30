import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PersonDocumentComponent,
  PersonDocumentDetailComponent,
  PersonDocumentUpdateComponent,
  PersonDocumentDeletePopupComponent,
  PersonDocumentDeleteDialogComponent,
  personDocumentRoute,
  personDocumentPopupRoute
} from './';

const ENTITY_STATES = [...personDocumentRoute, ...personDocumentPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PersonDocumentComponent,
    PersonDocumentDetailComponent,
    PersonDocumentUpdateComponent,
    PersonDocumentDeleteDialogComponent,
    PersonDocumentDeletePopupComponent
  ],
  entryComponents: [
    PersonDocumentComponent,
    PersonDocumentUpdateComponent,
    PersonDocumentDeleteDialogComponent,
    PersonDocumentDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPersonDocumentModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
