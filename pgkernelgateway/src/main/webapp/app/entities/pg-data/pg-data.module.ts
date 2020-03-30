import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgDataComponent,
  PgDataDetailComponent,
  PgDataUpdateComponent,
  PgDataDeletePopupComponent,
  PgDataDeleteDialogComponent,
  pgDataRoute,
  pgDataPopupRoute
} from './';

const ENTITY_STATES = [...pgDataRoute, ...pgDataPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PgDataComponent, PgDataDetailComponent, PgDataUpdateComponent, PgDataDeleteDialogComponent, PgDataDeletePopupComponent],
  entryComponents: [PgDataComponent, PgDataUpdateComponent, PgDataDeleteDialogComponent, PgDataDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgDataModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
