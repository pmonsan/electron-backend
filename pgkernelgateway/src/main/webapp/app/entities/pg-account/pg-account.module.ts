import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgAccountComponent,
  PgAccountDetailComponent,
  PgAccountUpdateComponent,
  PgAccountDeletePopupComponent,
  PgAccountDeleteDialogComponent,
  pgAccountRoute,
  pgAccountPopupRoute
} from './';

const ENTITY_STATES = [...pgAccountRoute, ...pgAccountPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PgAccountComponent,
    PgAccountDetailComponent,
    PgAccountUpdateComponent,
    PgAccountDeleteDialogComponent,
    PgAccountDeletePopupComponent
  ],
  entryComponents: [PgAccountComponent, PgAccountUpdateComponent, PgAccountDeleteDialogComponent, PgAccountDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgAccountModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
