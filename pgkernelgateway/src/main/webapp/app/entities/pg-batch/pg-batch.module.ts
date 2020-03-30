import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgBatchComponent,
  PgBatchDetailComponent,
  PgBatchUpdateComponent,
  PgBatchDeletePopupComponent,
  PgBatchDeleteDialogComponent,
  pgBatchRoute,
  pgBatchPopupRoute
} from './';

const ENTITY_STATES = [...pgBatchRoute, ...pgBatchPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PgBatchComponent,
    PgBatchDetailComponent,
    PgBatchUpdateComponent,
    PgBatchDeleteDialogComponent,
    PgBatchDeletePopupComponent
  ],
  entryComponents: [PgBatchComponent, PgBatchUpdateComponent, PgBatchDeleteDialogComponent, PgBatchDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgBatchModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
