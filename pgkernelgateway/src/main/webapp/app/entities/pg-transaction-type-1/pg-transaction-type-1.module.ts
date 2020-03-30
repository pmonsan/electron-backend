import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgTransactionType1Component,
  PgTransactionType1DetailComponent,
  PgTransactionType1UpdateComponent,
  PgTransactionType1DeletePopupComponent,
  PgTransactionType1DeleteDialogComponent,
  pgTransactionType1Route,
  pgTransactionType1PopupRoute
} from './';

const ENTITY_STATES = [...pgTransactionType1Route, ...pgTransactionType1PopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PgTransactionType1Component,
    PgTransactionType1DetailComponent,
    PgTransactionType1UpdateComponent,
    PgTransactionType1DeleteDialogComponent,
    PgTransactionType1DeletePopupComponent
  ],
  entryComponents: [
    PgTransactionType1Component,
    PgTransactionType1UpdateComponent,
    PgTransactionType1DeleteDialogComponent,
    PgTransactionType1DeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgTransactionType1Module {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
