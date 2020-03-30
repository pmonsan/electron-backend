import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgServiceComponent,
  PgServiceDetailComponent,
  PgServiceUpdateComponent,
  PgServiceDeletePopupComponent,
  PgServiceDeleteDialogComponent,
  pgServiceRoute,
  pgServicePopupRoute
} from './';

const ENTITY_STATES = [...pgServiceRoute, ...pgServicePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PgServiceComponent,
    PgServiceDetailComponent,
    PgServiceUpdateComponent,
    PgServiceDeleteDialogComponent,
    PgServiceDeletePopupComponent
  ],
  entryComponents: [PgServiceComponent, PgServiceUpdateComponent, PgServiceDeleteDialogComponent, PgServiceDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgServiceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
