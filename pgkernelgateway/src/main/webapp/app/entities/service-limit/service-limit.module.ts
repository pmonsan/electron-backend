import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  ServiceLimitComponent,
  ServiceLimitDetailComponent,
  ServiceLimitUpdateComponent,
  ServiceLimitDeletePopupComponent,
  ServiceLimitDeleteDialogComponent,
  serviceLimitRoute,
  serviceLimitPopupRoute
} from './';

const ENTITY_STATES = [...serviceLimitRoute, ...serviceLimitPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ServiceLimitComponent,
    ServiceLimitDetailComponent,
    ServiceLimitUpdateComponent,
    ServiceLimitDeleteDialogComponent,
    ServiceLimitDeletePopupComponent
  ],
  entryComponents: [
    ServiceLimitComponent,
    ServiceLimitUpdateComponent,
    ServiceLimitDeleteDialogComponent,
    ServiceLimitDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayServiceLimitModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
