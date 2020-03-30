import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  CustomerSubscriptionComponent,
  CustomerSubscriptionDetailComponent,
  CustomerSubscriptionUpdateComponent,
  CustomerSubscriptionDeletePopupComponent,
  CustomerSubscriptionDeleteDialogComponent,
  customerSubscriptionRoute,
  customerSubscriptionPopupRoute
} from './';

const ENTITY_STATES = [...customerSubscriptionRoute, ...customerSubscriptionPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CustomerSubscriptionComponent,
    CustomerSubscriptionDetailComponent,
    CustomerSubscriptionUpdateComponent,
    CustomerSubscriptionDeleteDialogComponent,
    CustomerSubscriptionDeletePopupComponent
  ],
  entryComponents: [
    CustomerSubscriptionComponent,
    CustomerSubscriptionUpdateComponent,
    CustomerSubscriptionDeleteDialogComponent,
    CustomerSubscriptionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayCustomerSubscriptionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
