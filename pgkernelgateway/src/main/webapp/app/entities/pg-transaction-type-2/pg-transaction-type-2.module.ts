import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgTransactionType2Component,
  PgTransactionType2DetailComponent,
  PgTransactionType2UpdateComponent,
  PgTransactionType2DeletePopupComponent,
  PgTransactionType2DeleteDialogComponent,
  pgTransactionType2Route,
  pgTransactionType2PopupRoute
} from './';

const ENTITY_STATES = [...pgTransactionType2Route, ...pgTransactionType2PopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PgTransactionType2Component,
    PgTransactionType2DetailComponent,
    PgTransactionType2UpdateComponent,
    PgTransactionType2DeleteDialogComponent,
    PgTransactionType2DeletePopupComponent
  ],
  entryComponents: [
    PgTransactionType2Component,
    PgTransactionType2UpdateComponent,
    PgTransactionType2DeleteDialogComponent,
    PgTransactionType2DeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgTransactionType2Module {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
