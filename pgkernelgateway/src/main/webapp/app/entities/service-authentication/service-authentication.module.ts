import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  ServiceAuthenticationComponent,
  ServiceAuthenticationDetailComponent,
  ServiceAuthenticationUpdateComponent,
  ServiceAuthenticationDeletePopupComponent,
  ServiceAuthenticationDeleteDialogComponent,
  serviceAuthenticationRoute,
  serviceAuthenticationPopupRoute
} from './';

const ENTITY_STATES = [...serviceAuthenticationRoute, ...serviceAuthenticationPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceAuthenticationComponent,
    ServiceAuthenticationDetailComponent,
    ServiceAuthenticationUpdateComponent,
    ServiceAuthenticationDeleteDialogComponent,
    ServiceAuthenticationDeletePopupComponent
  ],
  entryComponents: [
    ServiceAuthenticationComponent,
    ServiceAuthenticationUpdateComponent,
    ServiceAuthenticationDeleteDialogComponent,
    ServiceAuthenticationDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayServiceAuthenticationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
