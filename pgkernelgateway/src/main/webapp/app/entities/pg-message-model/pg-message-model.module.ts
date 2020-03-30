import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgMessageModelComponent,
  PgMessageModelDetailComponent,
  PgMessageModelUpdateComponent,
  PgMessageModelDeletePopupComponent,
  PgMessageModelDeleteDialogComponent,
  pgMessageModelRoute,
  pgMessageModelPopupRoute
} from './';

const ENTITY_STATES = [...pgMessageModelRoute, ...pgMessageModelPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PgMessageModelComponent,
    PgMessageModelDetailComponent,
    PgMessageModelUpdateComponent,
    PgMessageModelDeleteDialogComponent,
    PgMessageModelDeletePopupComponent
  ],
  entryComponents: [
    PgMessageModelComponent,
    PgMessageModelUpdateComponent,
    PgMessageModelDeleteDialogComponent,
    PgMessageModelDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgMessageModelModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
