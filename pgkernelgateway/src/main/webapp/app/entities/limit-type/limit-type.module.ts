import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  LimitTypeComponent,
  LimitTypeDetailComponent,
  LimitTypeUpdateComponent,
  LimitTypeDeletePopupComponent,
  LimitTypeDeleteDialogComponent,
  limitTypeRoute,
  limitTypePopupRoute
} from './';

const ENTITY_STATES = [...limitTypeRoute, ...limitTypePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LimitTypeComponent,
    LimitTypeDetailComponent,
    LimitTypeUpdateComponent,
    LimitTypeDeleteDialogComponent,
    LimitTypeDeletePopupComponent
  ],
  entryComponents: [LimitTypeComponent, LimitTypeUpdateComponent, LimitTypeDeleteDialogComponent, LimitTypeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayLimitTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
