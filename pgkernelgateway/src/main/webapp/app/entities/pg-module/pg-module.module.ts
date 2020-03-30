import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PgModuleComponent,
  PgModuleDetailComponent,
  PgModuleUpdateComponent,
  PgModuleDeletePopupComponent,
  PgModuleDeleteDialogComponent,
  pgModuleRoute,
  pgModulePopupRoute
} from './';

const ENTITY_STATES = [...pgModuleRoute, ...pgModulePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PgModuleComponent,
    PgModuleDetailComponent,
    PgModuleUpdateComponent,
    PgModuleDeleteDialogComponent,
    PgModuleDeletePopupComponent
  ],
  entryComponents: [PgModuleComponent, PgModuleUpdateComponent, PgModuleDeleteDialogComponent, PgModuleDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPgModuleModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
