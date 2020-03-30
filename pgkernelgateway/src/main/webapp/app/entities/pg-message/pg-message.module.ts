import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgMessageComponent,
  PgMessageDetailComponent,
  PgMessageUpdateComponent,
  PgMessageDeletePopupComponent,
  PgMessageDeleteDialogComponent,
  pgMessageRoute,
  pgMessagePopupRoute
} from './';

const ENTITY_STATES = [...pgMessageRoute, ...pgMessagePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PgMessageComponent,
    PgMessageDetailComponent,
    PgMessageUpdateComponent,
    PgMessageDeleteDialogComponent,
    PgMessageDeletePopupComponent
  ],
  entryComponents: [PgMessageComponent, PgMessageUpdateComponent, PgMessageDeleteDialogComponent, PgMessageDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgMessageModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
