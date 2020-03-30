import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  TransactionStatusComponent,
  TransactionStatusDetailComponent,
  TransactionStatusUpdateComponent,
  TransactionStatusDeletePopupComponent,
  TransactionStatusDeleteDialogComponent,
  transactionStatusRoute,
  transactionStatusPopupRoute
} from './';

const ENTITY_STATES = [...transactionStatusRoute, ...transactionStatusPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransactionStatusComponent,
    TransactionStatusDetailComponent,
    TransactionStatusUpdateComponent,
    TransactionStatusDeleteDialogComponent,
    TransactionStatusDeletePopupComponent
  ],
  entryComponents: [
    TransactionStatusComponent,
    TransactionStatusUpdateComponent,
    TransactionStatusDeleteDialogComponent,
    TransactionStatusDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayTransactionStatusModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
