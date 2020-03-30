import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  AccountBalanceComponent,
  AccountBalanceDetailComponent,
  AccountBalanceUpdateComponent,
  AccountBalanceDeletePopupComponent,
  AccountBalanceDeleteDialogComponent,
  accountBalanceRoute,
  accountBalancePopupRoute
} from './';

const ENTITY_STATES = [...accountBalanceRoute, ...accountBalancePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AccountBalanceComponent,
    AccountBalanceDetailComponent,
    AccountBalanceUpdateComponent,
    AccountBalanceDeleteDialogComponent,
    AccountBalanceDeletePopupComponent
  ],
  entryComponents: [
    AccountBalanceComponent,
    AccountBalanceUpdateComponent,
    AccountBalanceDeleteDialogComponent,
    AccountBalanceDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayAccountBalanceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
