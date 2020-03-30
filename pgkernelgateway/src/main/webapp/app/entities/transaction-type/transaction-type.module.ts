import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  TransactionTypeComponent,
  TransactionTypeDetailComponent,
  TransactionTypeUpdateComponent,
  TransactionTypeDeletePopupComponent,
  TransactionTypeDeleteDialogComponent,
  transactionTypeRoute,
  transactionTypePopupRoute
} from './';

const ENTITY_STATES = [...transactionTypeRoute, ...transactionTypePopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransactionTypeComponent,
    TransactionTypeDetailComponent,
    TransactionTypeUpdateComponent,
    TransactionTypeDeleteDialogComponent,
    TransactionTypeDeletePopupComponent
  ],
  entryComponents: [
    TransactionTypeComponent,
    TransactionTypeUpdateComponent,
    TransactionTypeDeleteDialogComponent,
    TransactionTypeDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayTransactionTypeModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
