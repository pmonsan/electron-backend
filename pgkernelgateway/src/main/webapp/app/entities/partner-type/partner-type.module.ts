import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PartnerTypeComponent,
  PartnerTypeDetailComponent,
  PartnerTypeUpdateComponent,
  PartnerTypeDeletePopupComponent,
  PartnerTypeDeleteDialogComponent,
  partnerTypeRoute,
  partnerTypePopupRoute
} from './';

const ENTITY_STATES = [...partnerTypeRoute, ...partnerTypePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PartnerTypeComponent,
    PartnerTypeDetailComponent,
    PartnerTypeUpdateComponent,
    PartnerTypeDeleteDialogComponent,
    PartnerTypeDeletePopupComponent
  ],
  entryComponents: [PartnerTypeComponent, PartnerTypeUpdateComponent, PartnerTypeDeleteDialogComponent, PartnerTypeDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPartnerTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
