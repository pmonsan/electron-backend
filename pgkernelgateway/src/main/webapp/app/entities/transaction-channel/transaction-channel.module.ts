import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  TransactionChannelComponent,
  TransactionChannelDetailComponent,
  TransactionChannelUpdateComponent,
  TransactionChannelDeletePopupComponent,
  TransactionChannelDeleteDialogComponent,
  transactionChannelRoute,
  transactionChannelPopupRoute
} from './';

const ENTITY_STATES = [...transactionChannelRoute, ...transactionChannelPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransactionChannelComponent,
    TransactionChannelDetailComponent,
    TransactionChannelUpdateComponent,
    TransactionChannelDeleteDialogComponent,
    TransactionChannelDeletePopupComponent
  ],
  entryComponents: [
    TransactionChannelComponent,
    TransactionChannelUpdateComponent,
    TransactionChannelDeleteDialogComponent,
    TransactionChannelDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayTransactionChannelModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
