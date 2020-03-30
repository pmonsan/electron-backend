import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgDetailMessageComponent,
  PgDetailMessageDetailComponent,
  PgDetailMessageUpdateComponent,
  PgDetailMessageDeletePopupComponent,
  PgDetailMessageDeleteDialogComponent,
  pgDetailMessageRoute,
  pgDetailMessagePopupRoute
} from './';

const ENTITY_STATES = [...pgDetailMessageRoute, ...pgDetailMessagePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PgDetailMessageComponent,
    PgDetailMessageDetailComponent,
    PgDetailMessageUpdateComponent,
    PgDetailMessageDeleteDialogComponent,
    PgDetailMessageDeletePopupComponent
  ],
  entryComponents: [
    PgDetailMessageComponent,
    PgDetailMessageUpdateComponent,
    PgDetailMessageDeleteDialogComponent,
    PgDetailMessageDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgDetailMessageModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
