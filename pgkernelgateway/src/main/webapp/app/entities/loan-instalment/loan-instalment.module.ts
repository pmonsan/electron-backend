import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { PgkernelgatewaySharedModule } from 'app/shared';
import {
  LoanInstalmentComponent,
  LoanInstalmentDetailComponent,
  LoanInstalmentUpdateComponent,
  LoanInstalmentDeletePopupComponent,
  LoanInstalmentDeleteDialogComponent,
  loanInstalmentRoute,
  loanInstalmentPopupRoute
} from './';

const ENTITY_STATES = [...loanInstalmentRoute, ...loanInstalmentPopupRoute];

@NgModule({
  imports: [PgkernelgatewaySharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    LoanInstalmentComponent,
    LoanInstalmentDetailComponent,
    LoanInstalmentUpdateComponent,
    LoanInstalmentDeleteDialogComponent,
    LoanInstalmentDeletePopupComponent
  ],
  entryComponents: [
    LoanInstalmentComponent,
    LoanInstalmentUpdateComponent,
    LoanInstalmentDeleteDialogComponent,
    LoanInstalmentDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PgkernelgatewayLoanInstalmentModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
