import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  TransactionCommissionComponent,
  TransactionCommissionDetailComponent,
  TransactionCommissionUpdateComponent,
  TransactionCommissionDeletePopupComponent,
  TransactionCommissionDeleteDialogComponent,
  transactionCommissionRoute,
  transactionCommissionPopupRoute
} from './';

const ENTITY_STATES = [...transactionCommissionRoute, ...transactionCommissionPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransactionCommissionComponent,
    TransactionCommissionDetailComponent,
    TransactionCommissionUpdateComponent,
    TransactionCommissionDeleteDialogComponent,
    TransactionCommissionDeletePopupComponent
  ],
  entryComponents: [
    TransactionCommissionComponent,
    TransactionCommissionUpdateComponent,
    TransactionCommissionDeleteDialogComponent,
    TransactionCommissionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayTransactionCommissionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
