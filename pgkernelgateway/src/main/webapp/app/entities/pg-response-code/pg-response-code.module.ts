import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgResponseCodeComponent,
  PgResponseCodeDetailComponent,
  PgResponseCodeUpdateComponent,
  PgResponseCodeDeletePopupComponent,
  PgResponseCodeDeleteDialogComponent,
  pgResponseCodeRoute,
  pgResponseCodePopupRoute
} from './';

const ENTITY_STATES = [...pgResponseCodeRoute, ...pgResponseCodePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PgResponseCodeComponent,
    PgResponseCodeDetailComponent,
    PgResponseCodeUpdateComponent,
    PgResponseCodeDeleteDialogComponent,
    PgResponseCodeDeletePopupComponent
  ],
  entryComponents: [
    PgResponseCodeComponent,
    PgResponseCodeUpdateComponent,
    PgResponseCodeDeleteDialogComponent,
    PgResponseCodeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgResponseCodeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
