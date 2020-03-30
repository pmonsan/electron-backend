import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  TransactionPropertyComponent,
  TransactionPropertyDetailComponent,
  TransactionPropertyUpdateComponent,
  TransactionPropertyDeletePopupComponent,
  TransactionPropertyDeleteDialogComponent,
  transactionPropertyRoute,
  transactionPropertyPopupRoute
} from './';

const ENTITY_STATES = [...transactionPropertyRoute, ...transactionPropertyPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransactionPropertyComponent,
    TransactionPropertyDetailComponent,
    TransactionPropertyUpdateComponent,
    TransactionPropertyDeleteDialogComponent,
    TransactionPropertyDeletePopupComponent
  ],
  entryComponents: [
    TransactionPropertyComponent,
    TransactionPropertyUpdateComponent,
    TransactionPropertyDeleteDialogComponent,
    TransactionPropertyDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayTransactionPropertyModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
