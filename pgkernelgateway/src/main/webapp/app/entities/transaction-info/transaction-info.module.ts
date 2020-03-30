import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  TransactionInfoComponent,
  TransactionInfoDetailComponent,
  TransactionInfoUpdateComponent,
  TransactionInfoDeletePopupComponent,
  TransactionInfoDeleteDialogComponent,
  transactionInfoRoute,
  transactionInfoPopupRoute
} from './';

const ENTITY_STATES = [...transactionInfoRoute, ...transactionInfoPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransactionInfoComponent,
    TransactionInfoDetailComponent,
    TransactionInfoUpdateComponent,
    TransactionInfoDeleteDialogComponent,
    TransactionInfoDeletePopupComponent
  ],
  entryComponents: [
    TransactionInfoComponent,
    TransactionInfoUpdateComponent,
    TransactionInfoDeleteDialogComponent,
    TransactionInfoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayTransactionInfoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
