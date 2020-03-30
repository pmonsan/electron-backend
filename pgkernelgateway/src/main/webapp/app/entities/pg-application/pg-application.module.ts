import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgApplicationComponent,
  PgApplicationDetailComponent,
  PgApplicationUpdateComponent,
  PgApplicationDeletePopupComponent,
  PgApplicationDeleteDialogComponent,
  pgApplicationRoute,
  pgApplicationPopupRoute
} from './';

const ENTITY_STATES = [...pgApplicationRoute, ...pgApplicationPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PgApplicationComponent,
    PgApplicationDetailComponent,
    PgApplicationUpdateComponent,
    PgApplicationDeleteDialogComponent,
    PgApplicationDeletePopupComponent
  ],
  entryComponents: [
    PgApplicationComponent,
    PgApplicationUpdateComponent,
    PgApplicationDeleteDialogComponent,
    PgApplicationDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgApplicationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
