import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgMessageModelDataComponent,
  PgMessageModelDataDetailComponent,
  PgMessageModelDataUpdateComponent,
  PgMessageModelDataDeletePopupComponent,
  PgMessageModelDataDeleteDialogComponent,
  pgMessageModelDataRoute,
  pgMessageModelDataPopupRoute
} from './';

const ENTITY_STATES = [...pgMessageModelDataRoute, ...pgMessageModelDataPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PgMessageModelDataComponent,
    PgMessageModelDataDetailComponent,
    PgMessageModelDataUpdateComponent,
    PgMessageModelDataDeleteDialogComponent,
    PgMessageModelDataDeletePopupComponent
  ],
  entryComponents: [
    PgMessageModelDataComponent,
    PgMessageModelDataUpdateComponent,
    PgMessageModelDataDeleteDialogComponent,
    PgMessageModelDataDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgMessageModelDataModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
