import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  ServiceIntegrationComponent,
  ServiceIntegrationDetailComponent,
  ServiceIntegrationUpdateComponent,
  ServiceIntegrationDeletePopupComponent,
  ServiceIntegrationDeleteDialogComponent,
  serviceIntegrationRoute,
  serviceIntegrationPopupRoute
} from './';

const ENTITY_STATES = [...serviceIntegrationRoute, ...serviceIntegrationPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceIntegrationComponent,
    ServiceIntegrationDetailComponent,
    ServiceIntegrationUpdateComponent,
    ServiceIntegrationDeleteDialogComponent,
    ServiceIntegrationDeletePopupComponent
  ],
  entryComponents: [
    ServiceIntegrationComponent,
    ServiceIntegrationUpdateComponent,
    ServiceIntegrationDeleteDialogComponent,
    ServiceIntegrationDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayServiceIntegrationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
