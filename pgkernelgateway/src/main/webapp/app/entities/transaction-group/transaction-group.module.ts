import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  TransactionGroupComponent,
  TransactionGroupDetailComponent,
  TransactionGroupUpdateComponent,
  TransactionGroupDeletePopupComponent,
  TransactionGroupDeleteDialogComponent,
  transactionGroupRoute,
  transactionGroupPopupRoute
} from './';

const ENTITY_STATES = [...transactionGroupRoute, ...transactionGroupPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransactionGroupComponent,
    TransactionGroupDetailComponent,
    TransactionGroupUpdateComponent,
    TransactionGroupDeleteDialogComponent,
    TransactionGroupDeletePopupComponent
  ],
  entryComponents: [
    TransactionGroupComponent,
    TransactionGroupUpdateComponent,
    TransactionGroupDeleteDialogComponent,
    TransactionGroupDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayTransactionGroupModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
