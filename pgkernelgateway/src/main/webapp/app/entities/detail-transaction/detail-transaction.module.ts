import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  DetailTransactionComponent,
  DetailTransactionDetailComponent,
  DetailTransactionUpdateComponent,
  DetailTransactionDeletePopupComponent,
  DetailTransactionDeleteDialogComponent,
  detailTransactionRoute,
  detailTransactionPopupRoute
} from './';

const ENTITY_STATES = [...detailTransactionRoute, ...detailTransactionPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DetailTransactionComponent,
    DetailTransactionDetailComponent,
    DetailTransactionUpdateComponent,
    DetailTransactionDeleteDialogComponent,
    DetailTransactionDeletePopupComponent
  ],
  entryComponents: [
    DetailTransactionComponent,
    DetailTransactionUpdateComponent,
    DetailTransactionDeleteDialogComponent,
    DetailTransactionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayDetailTransactionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
