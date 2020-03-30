import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  SubscriptionPriceComponent,
  SubscriptionPriceDetailComponent,
  SubscriptionPriceUpdateComponent,
  SubscriptionPriceDeletePopupComponent,
  SubscriptionPriceDeleteDialogComponent,
  subscriptionPriceRoute,
  subscriptionPricePopupRoute
} from './';

const ENTITY_STATES = [...subscriptionPriceRoute, ...subscriptionPricePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SubscriptionPriceComponent,
    SubscriptionPriceDetailComponent,
    SubscriptionPriceUpdateComponent,
    SubscriptionPriceDeleteDialogComponent,
    SubscriptionPriceDeletePopupComponent
  ],
  entryComponents: [
    SubscriptionPriceComponent,
    SubscriptionPriceUpdateComponent,
    SubscriptionPriceDeleteDialogComponent,
    SubscriptionPriceDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewaySubscriptionPriceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
