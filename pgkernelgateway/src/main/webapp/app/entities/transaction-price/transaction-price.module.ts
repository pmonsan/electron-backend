import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  TransactionPriceComponent,
  TransactionPriceDetailComponent,
  TransactionPriceUpdateComponent,
  TransactionPriceDeletePopupComponent,
  TransactionPriceDeleteDialogComponent,
  transactionPriceRoute,
  transactionPricePopupRoute
} from './';

const ENTITY_STATES = [...transactionPriceRoute, ...transactionPricePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransactionPriceComponent,
    TransactionPriceDetailComponent,
    TransactionPriceUpdateComponent,
    TransactionPriceDeleteDialogComponent,
    TransactionPriceDeletePopupComponent
  ],
  entryComponents: [
    TransactionPriceComponent,
    TransactionPriceUpdateComponent,
    TransactionPriceDeleteDialogComponent,
    TransactionPriceDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayTransactionPriceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
