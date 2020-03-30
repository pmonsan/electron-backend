import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  PartnerSecurityComponent,
  PartnerSecurityDetailComponent,
  PartnerSecurityUpdateComponent,
  PartnerSecurityDeletePopupComponent,
  PartnerSecurityDeleteDialogComponent,
  partnerSecurityRoute,
  partnerSecurityPopupRoute
} from './';

const ENTITY_STATES = [...partnerSecurityRoute, ...partnerSecurityPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    PartnerSecurityComponent,
    PartnerSecurityDetailComponent,
    PartnerSecurityUpdateComponent,
    PartnerSecurityDeleteDialogComponent,
    PartnerSecurityDeletePopupComponent
  ],
  entryComponents: [
    PartnerSecurityComponent,
    PartnerSecurityUpdateComponent,
    PartnerSecurityDeleteDialogComponent,
    PartnerSecurityDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayPartnerSecurityModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
