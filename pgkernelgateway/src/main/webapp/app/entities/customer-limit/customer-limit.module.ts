import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  CustomerLimitComponent,
  CustomerLimitDetailComponent,
  CustomerLimitUpdateComponent,
  CustomerLimitDeletePopupComponent,
  CustomerLimitDeleteDialogComponent,
  customerLimitRoute,
  customerLimitPopupRoute
} from './';

const ENTITY_STATES = [...customerLimitRoute, ...customerLimitPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CustomerLimitComponent,
    CustomerLimitDetailComponent,
    CustomerLimitUpdateComponent,
    CustomerLimitDeleteDialogComponent,
    CustomerLimitDeletePopupComponent
  ],
  entryComponents: [
    CustomerLimitComponent,
    CustomerLimitUpdateComponent,
    CustomerLimitDeleteDialogComponent,
    CustomerLimitDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayCustomerLimitModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
